.data
     	promptFile:   .asciiz "Please enter the file name: "
	fin:	.space 30       # filename for input
	file:	.space	30	#reading in the file
	buffer: .space  80	#80 byte buffer to print
.text
main:	
    	li $v0, 4
    	la $a0, promptFile	 #prompt user for file name
    	syscall
    	li $v0,8 
    	la $a0,fin
    	li $a1, 30 		#file name is now stored in fin
    	syscall	
    	la $a0, fin 		#remove
    	add $a0, $a0, 80	#remove
    	mainLoop:
    		lb $v0, 0($a0)
    		bnez $v0, doneMain
    		sub $a0, $a0, 1
    		j mainLoop
    	doneMain:
    		sb $zero, 0($a0)
  		li   $v0, 13       # system call for open file
  		la   $a0, fin      # input file name
  		li   $a1, 0        # Open for reading (flags are 0: read, 1: write)
  		li   $a2, 0        # mode is ignored
  		syscall            # open a file (file descriptor returned in $v0)
  		move $a0, $v0      # save the file descriptor 
  		addi $s6, $zero, 32
  		la $a2, buffer	
		jal _readLine
##################################################################################
_readLine:
.text
##a0 is the file descriptor
##a2 is buffer address 	
move $a0, $a0		#file descriptor
move $s2, $a2		#buffer address
add $a3, $zero, $v0 ###s2 contains if end of file
	readChar:
  		# Read from file just opened
  		li   $v0, 14       # system call for read from file
  		la   $a1, file     # address of buffer from which to read
  		li   $a2, 1        # hardcoded buffer length
  		syscall
  		lb $s7, file
		beq $s7, 10, addSpaces
 		sb $s7, buffer($t4)
 		addi $a1, $a1, 1	#increment file pointer
 		addi $t4,$t4,1		#increment file pointer
 		j readChar
	addSpaces:
		beq $t4, 80, done
		sb $s6, buffer($t4)  
		addi $t4, $t4, 1
		j addSpaces
	done:
		#return v0---tells if end of file
		move $a2, $s2		#buffer address
		jal _printLine
############################################################################	
_printLine:
.data
             		#    # " !       ' & % $     + * ) (     / . - ,     3 2 1 0     7 6 5 4     ; : 9 8     ? > = <     C B A @     G F E D     K J I H     O N M L     S R Q P     W V U T     [ Z Y X     _ ^ ] \     c b a `     g f e d     k j i h     0 n m l     s r q p     w v u t     { z y x     | } ~ <-
	line1:	.word	0x50502000, 0x2040c020, 0x00002020, 0x00000000, 0x70702070, 0xf870f810, 0x00007070, 0x70000000,	0x70f07070, 0x70f8f8f0, 0x88087088, 0x70888880, 0x70f070f0, 0x888888f8, 0x70f88888, 0x00207000, 0x00800020, 0x00300008, 0x80102080, 0x00000060, 0x00000000, 0x00000040, 0x10000000, 0x00404020
	line2:	.word	0x50502000, 0x20a0c878, 0x20a81040, 0x08000000, 0x88886088, 0x08888030, 0x00008888, 0x88400010, 0x88888888, 0x88808088, 0x90082088, 0x8888d880, 0x88888888, 0x88888820, 0x40088888, 0x00501080, 0x00800020, 0x00400008, 0x80000080, 0x00000020, 0x00000000, 0x00000040, 0x20000000, 0x00a82020
	line3:	.word	0xf8502000, 0x20a01080, 0x20701040, 0x10000000, 0x08082098, 0x1080f050, 0x20208888, 0x0820f820, 0x80888898, 0x80808088, 0xa0082088, 0x88c8a880, 0x80888888, 0x88888820, 0x40105050, 0x00881040, 0x70f07010, 0x70e07078, 0x903060f0, 0x70f8f020, 0x78b878f0, 0xa88888f0, 0x20f88888, 0x00102020
	line4:	.word	0x50002000, 0x00402070, 0xf8d81040, 0x2000f800, 0x301020a8, 0x20f80890, 0x00007870, 0x30100040, 0x80f088a8, 0x80f0f088, 0xc00820f8, 0x88a88880, 0x70f088f0, 0x88888820, 0x40202020, 0x00001020, 0x88880800, 0x88408888, 0xa0102088, 0x8888a820, 0x80488888, 0xa8888840, 0x40108850, 0x00001020
	line5:	.word	0xf8002000, 0x00a84008, 0x20701040, 0x40000030, 0x082020c8, 0x208808f8, 0x20200888, 0x2020f820, 0x8088f898, 0xb8808088, 0xa0882088, 0x88988880, 0x08a08880, 0xa8888820, 0x40402050, 0x00001010, 0x80887800, 0x8840f888, 0xc0102088, 0x8888a820, 0x70408888, 0xa8508840, 0x20208820, 0x00002020
	line6:	.word	0x50000000, 0x009098f0, 0x20a81040, 0x80000030, 0x88402088, 0x20888810, 0x20008888, 0x00400010, 0x88888880, 0x88808088, 0x90882088, 0x88888880, 0x88909880, 0xd8508820, 0x40802088, 0x00001008, 0x80888800, 0x78408088, 0xa0102088, 0x8888a820, 0x084078f0, 0xa8508840, 0x20407850, 0x00002020
	line7:	.word	0x50002000, 0x00681820, 0x00002020, 0x00200010, 0x70f87070, 0x20707010, 0x00007070, 0x20000000, 0x70f08878, 0x7080f8f0, 0x88707088, 0x708888f8, 0x70887880, 0x88207020, 0x70f82088, 0xf8007000, 0x78f07800, 0x08407878, 0x90907088, 0x7088a870, 0xf0400880, 0x50207830, 0x10f80888, 0x00004020
	line8:	.word	0x00000000, 0x00000000, 0x00000000, 0x00000020, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0xf0000000, 0x00600000, 0x00000000, 0x00000880, 0x00000000, 0x00007000, 0x00000000	
.text
#a2 contains address of 80 byte buffer
#a3  contains end of file
	move $a2, $a2
	move $a3, $a3
	la $s4, ($a2)		
	addi $t5, $zero, 32
	loop1:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line1($t1)
	sll $t6, $s0, 24
	lbu $s0, line1($t2)
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line1($t3)
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line1($t4)	
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1		
	beq $t7, 15, loop2Start
	j loop1
loop2Start:
	bne $t9, $zero, loop2Start
	la $s4, buffer
	j loop2
loop2:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line2($t1)
	sll $t6, $s0, 24
	lbu $s0, line2($t2)
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line2($t3)
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line2($t4)	
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1	
	beq $t7, 30, loop3Start	
	j loop2
loop3Start:
	bne $t9, $zero, loop3Start
	la $s4, buffer
	j loop3
loop3:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line3($t1)
	sll $t6, $s0, 24
	lbu $s0, line3($t2)	
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line3($t3)
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line3($t4)		
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1		
	beq $t7, 45, loop4Start	
	j loop3
loop4Start:
	bne $t9, $zero, loop4Start
	la $s4, buffer
	j loop4
loop4:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line4($t1)	
	sll $t6, $s0, 24
	lbu $s0, line4($t2)	
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line4($t3)	
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line4($t4)	
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1		
	beq $t7, 60, loop5Start	
	j loop4
loop5Start:
	bne $t9, $zero, loop5Start
	la $s4, buffer
	j loop5
loop5:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line5($t1)	
	sll $t6, $s0, 24
	lbu $s0, line5($t2)	
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line5($t3)	
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line5($t4)			
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1		
	beq $t7, 75, loop6Start	
	j loop5
loop6Start:
	bne $t9, $zero, loop6Start
	la $s4, buffer
	j loop6
loop6:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line6($t1)
	sll $t6, $s0, 24
	lbu $s0, line6($t2)
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line6($t3)
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line6($t4)	
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1		
	beq $t7, 90, loop7Start	
	j loop6
loop7Start:
	bne $t9, $zero, loop7Start
	la $s4, buffer
	j loop7
loop7:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line7($t1)
	sll $t6, $s0, 24
	lbu $s0, line7($t2)	
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line7($t3)	
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line7($t4)		
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1	
	beq $t7, 105, loop8Start	
	j loop7
loop8Start:
	bne $t9, $zero, loop8Start
	la $s4, buffer
	j loop8
loop8:	
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t1, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t2, $s7, $t5
	lb $s7, 0($s4)
	addi $s4, $s4, 1
	sub $t3, $s7, $t5
	lb $s7, 0($s4)
	sub $t4, $s7, $t5
	addi $s4, $s4, 1
	lbu $s0, line8($t1)
	sll $t6, $s0, 24
	lbu $s0, line8($t2)	
	sll $s5, $s0, 16
	or $s3, $t6, $s5
	lbu $s0, line8($t3)	
	sll $t6, $s0, 8
	or $s5, $t6, $s3
	lbu $s0, line8($t4)
	or $s3, $s5, $s0	
	add $t8, $zero, $s3
	addi $t9, $t9,1
	addi $t7, $t7, 1	
	beq $t7, 120, done1	
	j loop8
done1:
	add $s3, $zero, $zero
	add $t8, $zero, $s3
	addi $t9, $zero, 1
	addi $t7, $t7, 1
	beq $t7, 135, done2
	beq $t7, 150, done2
	beq $t7, 165, done2
	beq $t7, 180, done2
	beq $t7, 195, done2
	beq $t7, 210, done3
done2:
	bne $t9, $zero, done2
	j done1
done3:
	beq $s2, 0, endProgram
	add $t7, $zero, $zero
	add $t4, $zero, $zero
	jal _readLine
endProgram:
    	li   $v0, 16       # system call for close file
    	move $a0, $s0      # file descriptor to close
    	syscall            # close file
	li $v0, 10		
	syscall	
