#Kerilee Bookleiner
.data
	guessMsg: .asciiz "Please enter your guess between 0 and 9: "
	winMsg: .asciiz "Congratulations! You win!"
	tooLow: .asciiz "Your guess is too low. \n"
	tooHigh: .asciiz "Your guess is too high. \n"
	loseGame: .asciiz "You lose. The number was "
	endSentence: .asciiz "."
.text

	addi $v0, $zero, 42	#generate random number
	add $a0, $zero, $zero
	addi $a1, $zero, 10	#int between 0 and 9(inclusive)
	syscall			
	add $s1, $zero, $a0	#random num put into #$s1
	
Loop:
	addi $v0, $zero, 4	#ask player guess (print string)
	la $a0, guessMsg
	syscall	
	
	addi $v0, $zero, 5	#read input from user
	syscall			#input is stored in $s2
	add $s2, $zero, $v0
	
	beq $s1, $s2, correct	#check guess--if correct end game
	bne $s1, $s2, inCorrect
	j Loop
	
correct:
	addi $v0, $zero, 4
	la $a0, winMsg
	syscall
	
	addi $v0, $zero, 10
	syscall
	
inCorrect:
	addi $s3, $s3, 1
	slt $t0, $s2, $s1
	beq $t0, 0, isHigher
	beq $t0, 1, isLower
	

isHigher:
	addi $v0, $zero, 4	#print that guess is too high
	la $a0, tooHigh
	syscall
	beq $s3, 3, gameOver
	j Loop
	
isLower:
	addi $v0, $zero, 4	#print that guess is too low
	la $a0, tooLow
	syscall
	beq $s3, 3, gameOver
	j Loop

gameOver:
	addi $v0, $zero, 4	#print that game is over
	la $a0, loseGame
	syscall
	addi $v0, $zero, 1
	add $a0, $zero, $s1
	syscall
	addi $v0, $zero, 4	
	la $a0, endSentence
	syscall
