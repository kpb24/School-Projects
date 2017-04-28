#Kerilee Bookleiner
.data
	inputMsg: .asciiz "Enter a nonnegative Integer: "
	factorial: .asciiz "! = "
	invalid: .asciiz "Invalid integer; try again.\n"
.text
main:
	addi $v0, $zero, 4 	# print string
	la $a0, inputMsg
	syscall 
	addi $v0, $zero, 5 	# read integer input
	syscall 
	add $s2, $zero, $v0	#s2 contains input
	add $a0, $zero, $s2	#move input into a0
	slt $s3, $a0, $zero	#if negative, reprompt
	beq $s3, 1, negValue
	jal _Fac
	add $s1, $zero, $v0
	addi $v0, $zero, 1 	#print answer
	add $a0, $zero, $s2
	syscall 
	addi $v0, $zero, 4 	# print string
	la $a0, factorial
	syscall
	addi $v0, $zero, 1 	#print answer
	add $a0, $zero, $s1
	syscall 
	li $v0, 10		#end program
	syscall	
negValue:
	addi $v0, $zero, 4
	la $a0, invalid
	syscall
	j main
	
		
				
_Fac:
.text
	addi $sp, $sp, -8	#frame
	sw $s1, 0($sp)		
	sw $ra, 4($sp)		
	add $s1, $zero, $a0	#put argument into s1
	addi $v0, $zero, 1	
	beq $s1, $v0, base	# go to base
	addi $a0, $s1, -1	# f-1
	jal _Fac		# call function recursively 
	mult $v0, $s1		# *n
	mflo $v0		# result from low register
	j exit		
base:
	li $v0,1          	# return v0	
exit:
	lw $ra, 4($sp)		
	lw $s1, 0($sp)		
	addi $sp, $sp, 8	# frame
	jr $ra			# go to main
