import argparse
import math
import os
import pickle
import time
import re
from collections import deque
from random import randint
import sys
import numpy as np
import pygame
from pygame.locals import *
FPS = 60
ANIMATION_SPEED = 0.18  # pixels per millisecond
WIN_WIDTH = 284 * 2  # BG image size: 284x512 px; tiled twice
WIN_HEIGHT = 512
s = np.full((2, 460, 500, 3, 3, 2), 0, dtype=np.float)
a = np.full((2, 460, 500, 3, 3, 2), 1, dtype=np.float)
class Bird(pygame.sprite.Sprite):
	"""Represents the bird controlled by the player.
	The bird is the 'hero' of this game.  The player can make it climb
	(ascend quickly), otherwise it sinks (descends more slowly).  It must
	pass through the space in between pipes (for every pipe passed, one
	point is scored); if it crashes into a pipe, the game ends.
	Attributes:
	x: The bird's X coordinate.
	y: The bird's Y coordinate.
	msec_to_climb: The number of milliseconds left to climb, where a
		complete climb lasts Bird.CLIMB_DURATION milliseconds.
	Constants:
	WIDTH: The width, in pixels, of the bird's image.
	HEIGHT: The height, in pixels, of the bird's image.
	SINK_SPEED: With which speed, in pixels per millisecond, the bird
		descends in one second while not climbing.
	CLIMB_SPEED: With which speed, in pixels per millisecond, the bird
		ascends in one second while climbing, on average.  See also the
		Bird.update docstring.
	CLIMB_DURATION: The number of milliseconds it takes the bird to
		execute a complete climb.
	"""

	WIDTH = HEIGHT = 32
	SINK_SPEED = 0.18
	CLIMB_SPEED = 0.3
	CLIMB_DURATION = 333.3

	def __init__(self, x, y, msec_to_climb, images):
		"""Initialise a new Bird instance.
		Arguments:
		x: The bird's initial X coordinate.
		y: The bird's initial Y coordinate.
		msec_to_climb: The number of milliseconds left to climb, where a
			complete climb lasts Bird.CLIMB_DURATION milliseconds.  Use
			this if you want the bird to make a (small?) climb at the
			very beginning of the game.
		images: A tuple containing the images used by this bird.  It
			must contain the following images, in the following order:
				0. image of the bird with its wing pointing upward
				1. image of the bird with its wing pointing downward
		"""
		super(Bird, self).__init__()
		self.x, self.y = x, y
		self.msec_to_climb = msec_to_climb
		self._img_wingup, self._img_wingdown = images
		self._mask_wingup = pygame.mask.from_surface(self._img_wingup)
		self._mask_wingdown = pygame.mask.from_surface(self._img_wingdown)
		self.alive = True

	def update(self, delta_frames=1):
		"""Update the bird's position.
		This function uses the cosine function to achieve a smooth climb:
		In the first and last few frames, the bird climbs very little, in the
		middle of the climb, it climbs a lot.
		One complete climb lasts CLIMB_DURATION milliseconds, during which
		the bird ascends with an average speed of CLIMB_SPEED px/ms.
		This Bird's msec_to_climb attribute will automatically be
		decreased accordingly if it was > 0 when this method was called.
		Arguments:
		delta_frames: The number of frames elapsed since this method was
			last called.
		"""
		if self.msec_to_climb > 0:
			frac_climb_done = 1 - self.msec_to_climb / Bird.CLIMB_DURATION
			self.y -= (Bird.CLIMB_SPEED * frames_to_msec(delta_frames) *
					   (1 - math.cos(frac_climb_done * math.pi)))
			self.msec_to_climb -= frames_to_msec(delta_frames)
		else:
			self.y += Bird.SINK_SPEED * frames_to_msec(delta_frames)

	@property
	def image(self):
		"""Get a Surface containing this bird's image.
		This will decide whether to return an image where the bird's
		visible wing is pointing upward or where it is pointing downward
		based on pygame.time.get_ticks().  This will animate the flapping
		bird, even though pygame doesn't support animated GIFs.
		"""
		if pygame.time.get_ticks() % 500 >= 250:
			return self._img_wingup
		else:
			return self._img_wingdown

	@property
	def mask(self):
		"""Get a bitmask for use in collision detection.
		The bitmask excludes all pixels in self.image with a
		transparency greater than 127."""
		if pygame.time.get_ticks() % 500 >= 250:
			return self._mask_wingup
		else:
			return self._mask_wingdown
		
	@property
	def rect(self):
		"""Get the bird's position, width, and height, as a pygame.Rect."""
		return Rect(self.x, self.y, Bird.WIDTH, Bird.HEIGHT)

class PipePair(pygame.sprite.Sprite):
	"""Represents an obstacle.
	A PipePair has a top and a bottom pipe, and only between them can
	the bird pass -- if it collides with either part, the game is over.
	Attributes:
	x: The PipePair's X position.  This is a float, to make movement
		smoother.  Note that there is no y attribute, as it will only
		ever be 0.
	image: A pygame.Surface which can be blitted to the display surface
		to display the PipePair.
	mask: A bitmask which excludes all pixels in self.image with a
		transparency greater than 127.  This can be used for collision
		detection.
	top_pieces: The number of pieces, including the end piece, in the
		top pipe.
	bottom_pieces: The number of pieces, including the end piece, in
		the bottom pipe.
	Constants:
	WIDTH: The width, in pixels, of a pipe piece.  Because a pipe is
		only one piece wide, this is also the width of a PipePair's
		image.
	PIECE_HEIGHT: The height, in pixels, of a pipe piece.
	ADD_INTERVAL: The interval, in milliseconds, in between adding new
		pipes.
	"""

	WIDTH = 80
	PIECE_HEIGHT = 32
	ADD_INTERVAL = 3000

	def __init__(self, pipe_end_img, pipe_body_img):
		"""Initialises a new random PipePair.
		The new PipePair will automatically be assigned an x attribute of
		float(WIN_WIDTH - 1).
		Arguments:
		pipe_end_img: The image to use to represent a pipe's end piece.
		pipe_body_img: The image to use to represent one horizontal slice
			of a pipe's body.
		"""
		
		self.passed = set()
		
		self.x = float(WIN_WIDTH - 1)
		self.score_counted = set()
		
		self.image = pygame.Surface((PipePair.WIDTH, WIN_HEIGHT), SRCALPHA)
		self.image.convert()  # speeds up blitting
		self.image.fill((0, 0, 0, 0))
		total_pipe_body_pieces = int(
			(WIN_HEIGHT -  # fill window from top to bottom
			 3 * Bird.HEIGHT -  # make room for bird to fit through
			 3 * PipePair.PIECE_HEIGHT) /  # 2 end pieces + 1 body piece
			PipePair.PIECE_HEIGHT  # to get number of pipe pieces
		)
		
		self.bottom_pieces = randint(1, total_pipe_body_pieces)
		
		self.top_pieces = total_pipe_body_pieces - self.bottom_pieces
		
		# bottom pipe
		for i in range(1, self.bottom_pieces + 1):
			piece_pos = (0, WIN_HEIGHT - i * PipePair.PIECE_HEIGHT)
			self.image.blit(pipe_body_img, piece_pos)
		bottom_pipe_end_y = WIN_HEIGHT - self.bottom_height_px
		self.bottom_pipe_end_y = bottom_pipe_end_y
		
		bottom_end_piece_pos = (0, bottom_pipe_end_y - PipePair.PIECE_HEIGHT)
		
		self.image.blit(pipe_end_img, bottom_end_piece_pos)
		
		# top pipe
		for i in range(self.top_pieces):
			self.image.blit(pipe_body_img, (0, i * PipePair.PIECE_HEIGHT))
		top_pipe_end_y = self.top_height_px
		self.top_pipe_end_y = top_pipe_end_y
		
		self.image.blit(pipe_end_img, (0, top_pipe_end_y))
		
		# compensate for added end pieces
		self.top_pieces += 1
		self.bottom_pieces += 1
		
		# for collision detection
		self.mask = pygame.mask.from_surface(self.image)
		
	@property
	def top_height_px(self):
		"""Get the top pipe's height, in pixels."""
		return self.top_pieces * PipePair.PIECE_HEIGHT
		
	@property
	def bottom_height_px(self):
		"""Get the bottom pipe's height, in pixels."""
		return self.bottom_pieces * PipePair.PIECE_HEIGHT
		
	@property
	def visible(self):
		"""Get whether this PipePair on screen, visible to the player."""
		return -PipePair.WIDTH < self.x < WIN_WIDTH
		
	@property
	def rect(self):
		"""Get the Rect which contains this PipePair."""
		return Rect(self.x, 0, PipePair.WIDTH, PipePair.PIECE_HEIGHT)

	def update(self, delta_frames=1):
		"""Update the PipePair's position.
		Arguments:
		delta_frames: The number of frames elapsed since this method was
			last called.
		"""
		self.x -= ANIMATION_SPEED * frames_to_msec(delta_frames)

	def collides_with(self, bird):
		"""Get whether the bird collides with a pipe in this PipePair.
		Arguments:
		bird: The Bird which should be tested for collision with this
			PipePair.
		"""
		return pygame.sprite.collide_mask(self, bird)


def load_images():
	"""Load all images required by the game and return a dict of them.
	The returned dict has the following keys:
	background: The game's background image.
	bird-wingup: An image of the bird with its wing pointing upward.
		Use this and bird-wingdown to create a flapping bird.
	bird-wingdown: An image of the bird with its wing pointing downward.
		Use this and bird-wingup to create a flapping bird.
	pipe-end: An image of a pipe's end piece (the slightly wider bit).
		Use this and pipe-body to make pipes.
	pipe-body: An image of a slice of a pipe's body.  Use this and
		pipe-body to make pipes.
	"""

	def load_image(img_file_name):
		"""Return the loaded pygame image with the specified file name.
		This function looks for images in the game's images folder
		(./images/).  All images are converted before being returned to
		speed up blitting.
		Arguments:
		img_file_name: The file name (including its extension, e.g.
			'.png') of the required image, without a file path.
		"""
		file_name = os.path.join('.', 'images', img_file_name)
		img = pygame.image.load(file_name)
		img.convert()
		return img

	return {'background': load_image('background.png'),
			'pipe-end': load_image('pipe_end.png'),
			'pipe-body': load_image('pipe_body.png'),
			# images for animating the flapping bird -- animated GIFs are
			# not supported in pygame
			'bird-wingup': load_image('bird_wing_up.png'),
			'bird-wingdown': load_image('bird_wing_down.png')}


def frames_to_msec(frames, fps=FPS):
	"""Convert frames to milliseconds at the specified framerate.
	Arguments:
	frames: How many frames to convert to milliseconds.
	fps: The framerate to use for conversion.  Default: FPS.
	"""
	return 1000.0 * frames / fps


def msec_to_frames(milliseconds, fps=FPS):
	"""Convert milliseconds to frames at the specified framerate.
	Arguments:
	milliseconds: How many milliseconds to convert to frames.
	fps: The framerate to use for conversion.  Default: FPS.
	"""
	return fps * milliseconds / 1000.0

class State:
	def __init__(self, bird):
		self.bird = bird
		self.pipesCleared = 0
		self.previousState = None
		self.flapWings = None
		self.nextPipe = None
		
	def fly(self, state):
		state = self.indexBirdState(state)
		if s[((1,)+state)] +8/a[((1,)+state)] > s[((0,)+state)]+8/a[((0,)+state)]:
			flapWings = 1
		elif s[((0,)+state)] +8/a[((0,)+state)] > s[((1,)+state)]+8/a[((1,)+state)]:
			flapWings = 0
		else:
			sumBounds = WIN_HEIGHT - self.nextPipe.bottom_pieces * PipePair.PIECE_HEIGHT + self.nextPipe.top_pieces * PipePair.PIECE_HEIGHT
			#avg then divide by adjusted window height
			if int(self.bird.y/(WIN_HEIGHT/3)) > int(((sumBounds)/2)/(WIN_HEIGHT/3)):
				flapWings = 1
			else:
				flapWings = 0
		if (self.bird.y-Bird.CLIMB_DURATION*Bird.CLIMB_SPEED) <= 0:
			flapWings = 0
		if self.bird.y+(Bird.SINK_SPEED*frames_to_msec(1)) >= WIN_HEIGHT-self.bird.HEIGHT:
			flapWings = 1
		#Bird should flap wings
		if flapWings == 1:
			self.bird.msec_to_climb = Bird.CLIMB_DURATION
		a[(flapWings,)+state] += 1
		return flapWings
		
	#Returns the state of the bird
	def getState(self):
		sumBounds = self.nextPipe.top_pieces * PipePair.PIECE_HEIGHT + WIN_HEIGHT - self.nextPipe.bottom_pieces * PipePair.PIECE_HEIGHT
		yDifference = int(((sumBounds)/2) - self.bird.y)
		xDifference = int((self.nextPipe.x + PipePair.WIDTH) - self.bird.x)
		pipeH = int(((sumBounds)/2)/(WIN_HEIGHT/3))
		birdH = min(int(self.bird.y/(WIN_HEIGHT/3)),2)
		if self.bird.msec_to_climb>0:
			flying = 1
		else:
			flying = 0
		return xDifference, yDifference, birdH, pipeH, flying
		
	@staticmethod
	def indexBirdState(state):
		yDifference, xDifference, birdH, pipeH, flying = state
		actions, height, width, _, _, _ = s.shape
		y = int((height/2)+(yDifference/3)-1)
		x = int((width/2)+(xDifference/3)-1)
		return x, y, birdH, pipeH, flying
		
	#allows the bird to learn - q learning
	def train(self, previousState, newState, flapWings, reward):
		oldValue = (flapWings,) + (self.indexBirdState(previousState)) #get the old value
		x, y, birdH, pipeH, flying = (self.indexBirdState(newState)) # get the index values of the new state
		s[oldValue] = (0.1)*s[oldValue]+0.9*(max(s[:, x, y, birdH, pipeH, flying])+reward) #q learning
		
#Calculates the reward based on the bird's actions. Dying and going too close to the bottom
#or top of the screen have penalties to the reward. If the bird is alive or it clears
#pipe it gets positive reward.
def getReward(birdie, pipes, passedPipe):
	reward = 0
	if birdie.bird.alive:
		reward+=1000
	else:
		reward-=10000
	if birdie.bird.y <= 0 or birdie.bird.y > WIN_HEIGHT - 30:
		reward-=25000
	if passedPipe:
		reward+=10000
	return reward, pipes
	
def main():
	pygame.init()
	
	display_surface = pygame.display.set_mode((WIN_WIDTH, WIN_HEIGHT))
	pygame.display.set_caption('Pygame Flappy Bird')
	
	clock = pygame.time.Clock()
	score_font = pygame.font.SysFont(None, 32, bold=True)  # default font
	images = load_images()
	global s, a
	gamesPlayed = 0
	mostPipesCleared = 0
	
	if os.path.exists("flappybird.pickle"):
		with open("flappybird.pickle", 'rb') as handle:
			s, a = pickle.load(handle)
	else:
		print("flappybird.pickle", 'does not exist.')
			
	while True:
		score = 0 
		gamesPlayed += 1
		bird = Bird(50, int(WIN_HEIGHT/2 - Bird.HEIGHT/2), 2,(images['bird-wingup'], images['bird-wingdown']))
		birdie = State(bird)
		pipes = deque()
		frame_clock = 0  # this counter is only incremented if the game isn't paused
		done = paused = False
		while not done:
			clock.tick(FPS)
			
			# Handle this 'manually'.  If we used pygame.time.set_timer(),
			# pipe addition would be messed up when paused.
			if not (paused or frame_clock % msec_to_frames(PipePair.ADD_INTERVAL)):
				pp = PipePair(images['pipe-end'], images['pipe-body'])
				pipes.append(pp)
				
			for e in pygame.event.get():
				if e.type == QUIT or (e.type == KEYUP and e.key == K_ESCAPE):
					done = True
					break
				elif e.type == KEYUP and e.key in (K_PAUSE, K_p):
					paused = not paused
				elif e.type == MOUSEBUTTONUP or (e.type == KEYUP and e.key in (K_UP, K_RETURN, K_SPACE)):
					birdie.bird.msec_to_climb = Bird.CLIMB_DURATION
			if paused:
				continue
				
			if len(pipes) > 1 and ((birdie.nextPipe.x + PipePair.WIDTH) - birdie.bird.x < 0):
				birdie.nextPipe = pipes[1]
			else:
				birdie.nextPipe = pipes[0]
			if birdie.previousState is None:
				birdie.previousState = birdie.getState()
			birdie.action = birdie.fly(birdie.previousState)
			
			# check for collisions
			pipe_collision = any(p.collides_with(birdie.bird) for p in pipes)
			if pipe_collision or 0 >= birdie.bird.y or birdie.bird.y >= WIN_HEIGHT - Bird.HEIGHT:
				birdie.bird.alive = False 
				done = True
			for x in (0, WIN_WIDTH / 2):
				display_surface.blit(images['background'], (x, 0))
			while pipes and not pipes[0].visible:
				pipes.popleft()
			for p in pipes:
				p.update() 
				display_surface.blit(p.image, p.rect)
				
			birdie.bird.update()
			state = birdie.getState() #get the state of the bird
			
			passedPipe = False
			if (pipes[0].x + PipePair.WIDTH) - birdie.bird.x < 0:
				if birdie.bird not in pipes[0].passed:
					birdie.pipesCleared += 1 #bird cleared a pipe
					pipes[0].passed |= {birdie.bird} #add this to the passed set of pipes
					passedPipe = True
			
			reward, pipes = getReward(birdie, pipes, passedPipe) #get the reward and the updated pipes
			birdie.train(birdie.previousState, state, birdie.action, reward)
			birdie.previousState = state #set to previous state to use for next time
			if birdie.bird.alive:
				display_surface.blit(birdie.bird.image, birdie.bird.rect)
				
			# update and display score
			for p in pipes:
				if birdie.bird.alive:
					if p.x + PipePair.WIDTH < birdie.bird.x and birdie.bird not in p.score_counted:
						score += 1
						p.score_counted |= {birdie.bird}
			score_surface = score_font.render(str(birdie.pipesCleared), True, (255, 255, 255))
			score_x = WIN_WIDTH / 2 - score_surface.get_width() / 2
			display_surface.blit(score_surface, (score_x, PipePair.PIECE_HEIGHT))
			pygame.display.flip()
			frame_clock += 1
			
		print("Game %i" % gamesPlayed)
		print("Passed: %i\n" % birdie.pipesCleared)
	print('Game over! Score: %i' % score)
	pygame.quit()


if __name__ == '__main__':
	main()
