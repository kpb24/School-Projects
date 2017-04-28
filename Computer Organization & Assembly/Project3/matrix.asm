.text
#Kerilee Bookleiner
	addi $s4, $zero, 1	#counter for generating random chars
	li $s0, 0xffff8000 	# $s0 - Base address of the terminal
	li $t4, 4
	addi $s1, $zero, 13

generateRandChar:
	addi $v0, $zero, 42 	# Syscall 42: Random int range
	add $a0, $zero, 0
	addi $a1, $zero, 94 	# Set upper bound to 5 (exclusive)
	syscall 		# Generate a random number and put it in $a0
	add $s3, $a0, 33
	sll $s3, $s3, 24 	# Move ascii value to the top 8-bit
	or $s3, $s3, 0x002200   # Put color to the bottom 24-bit
	sw $s3, ($s0) 		# Put character on the terminal
	beq $s4, 3200, getPlaces
	addi $s4, $s4, 1
	addi $s0, $s0, 4
	sw $s5, ($s0)  		# Put character on the terminal 
	j generateRandChar

getPlaces:
	jal Column_Number
	move $t3, $v1		#first column
	add $t0, $zero, $a0	#make sure they aren't the same column
	jal Column_Number
	move $s5, $v1		#second column
	
	jal Column_Number
	move $t7, $v1		#second column
	li $s3, 0x002200	#set s3 to the dark green color
	
	move $a2, $t3
	jal Find_Position
	move $t5, $s2
	move $a3, $s2
	
	move $a2, $s5
	jal Find_Position
	move $t6, $s2
	move $s4, $s2
	
	move $a2, $t7
	jal Find_Position
	move $t3, $s2
	move $s5, $s2
	
	jal Generate_Speed	#generate a random speed
	move $t0, $v0
	add $t7, $zero, $zero
	j changeColors
changeColors:

	lw $s7, ($t5)
	srl $s7, $s7, 16	#clear color bits
	sll $s7, $s7, 16
	add $s3, $s3, 0x001100
	or $s7, $s7, $s3
	sw $s7, ($t5)
	addi $t5, $t5, 320
	
	lw $t8, ($t6)
	srl $t8, $t8, 16	#clear color bits
	sll $t8, $t8, 16
	or $t8, $t8, $s3
	sw $t8, ($t6)
	addi $t6, $t6, 320
	
	lw $t9, ($t3)
	srl $t9, $t9, 16	#clear color bits
	sll $t9, $t9, 16
	or $t9, $t9, $s3
	sw $t9, ($t3)
	addi $t3, $t3, 320
	
	li $v0, 32		#delay
	move $a0, $t0
	syscall
	
	addi $t1, $t1, 1
	beq $t1, 520, end	#when $t1 becomes 507 the column has completely "fallen"
	beq $t1, $s1, goBack	#when $t1 is 13 the iteration is complete and move to loop 3
	j changeColors	
	
goBack:
	move $t6, $s4
	move $t5, $a3
	move $t3, $s5
	addi $s1, $s1, 13
	addi $t7, $t7, 320	
	add $t6, $t6, $t7
	add $t5, $t5, $t7
	add $t3, $t3, $t7
	li $s3, 0x001100	#make $s3 the original color, except one less because of addition above
	j changeColors	
					
end:
	li $v0, 10              
    	syscall                 # Exit                    
	
Column_Number:
	#takes no arguments
	#returns the location in memory in $v1
	addi $v0, $zero, 42	#generate random column number
	add $a0, $zero, 0
	addi $a1, $zero, 81	
	syscall
	beq $a0, $t0, getPlaces
	add $s6, $zero, $a0
	mul $t2, $t4, $a0	#t2 is the location in memory
	move $v1, $t2
	jr $ra

Find_Position:
	#takes argument a2 which is position in memory
	#returns the space of column
	li $s2, 0xffff8000
	add $s2, $s2, $a2
	sub $s2, $s2, 4
	add $t9, $a2, -12800
	sub $t9, $zero, $t9
	move $v1, $t9
	jr $ra
	
Generate_Speed:
	#takes no arguments
	#returns a speed for chars to "fall"
	addi $v0, $zero, 42 	#for syscall
	add $a0, $zero, 0
	addi $a1, $zero, 30	#upper bound
	syscall
	move $v0, $a0
	addi $v0, $v0, 10
	jr $ra 