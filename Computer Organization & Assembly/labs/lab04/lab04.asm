#Kerilee Bookleiner
.data
enterString: .asciiz "Enter a String: "
outputChar1: .asciiz "This string has "
outputChar2: .asciiz " characters.\n"
startIndex1: .asciiz "Specify start index: "
startIndex2: .asciiz "Specify end index: "
substring: .asciiz "Your substring is: "

.text
main:
	addi $v0, $zero, 4
	la $a0, enterString
	syscall
	jal _readString		
	addi $v0, $zero, 4	#print number of chars
	la $a0, outputChar1
	syscall
	addi $v0, $zero, 1  
	add $a0, $zero, $t0	#t0 is the number of chars
	syscall 
	addi $v0, $zero, 4
	la $a0, outputChar2
	syscall
	addi $v0, $zero, 4
	la $a0, startIndex1
	syscall
	addi $v0, $zero, 5 # get start index ($a2)
	syscall 
	add $a2, $zero, $v0 
	addi $v0, $zero, 4
	la $a0, startIndex2
	syscall
	addi $v0, $zero, 5 	# get end index ($a3)
	syscall
	add $a3, $zero, $v0 
	sw $ra, 0($sp)	
	jal _subString
	lw $ra, 0($sp)
	li $v0, 10		#end program
	syscall	

_strLength:
.text 
	add $v0, $zero, $zero
	add $t0, $zero, $a0
	add $t4, $zero, $a0		#the string
loop:
	lbu $t3, 0($t0)
	beq $zero, $t3, exit
	addi $v0, $v0, 1	#$v0 is length of string (count)
	addi $t0, $t0, 1	#increment pointer
	j loop
exit:  
	lw $t0, 4($sp)
	jr $ra
		
_readString:
.data
	buffer: .space 64
.text
	la $a0, buffer		#use allotted space to store the string
	li $a1, 15		#max char = 15
	li $v0, 8
	syscall
	sw $a0, 4($sp)
	sw $ra, 0($sp)
	jal _strLength
	lw $ra, 0($sp)
	addi $t0, $v0, -1	#$t0 is now length of string
	jr $ra

_subString:
.text
	addi $v0, $zero, 4	#print substring
	la $a0, substring
	syscall
	add $t1, $zero, $a1
	add $t0, $t4, $a2
	add $t2, $zero, $a2
loop2:
	beq $t2, $a3, setByte
	lb $t1, ($t0) 
	lb $a0, ($t0)
	li $v0, 11		#print characters in the range entered
	syscall
	addi $t0, $t0, 1
	addi $t1, $t1, 1
	addi $t2, $t2, 1
	j loop2
setByte: 
	add $v0, $zero, $a1	#return address output buffer
	jr $ra
	
	
