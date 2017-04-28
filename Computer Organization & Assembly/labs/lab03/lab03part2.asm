#Kerilee Bookleiner
.data
calcMsg: .asciiz "x^y calculator \n"
enterX: .asciiz "Please enter x: "
enterY: .asciiz "Please enter y: "
negInt: .asciiz "Integer must be nonnegative. \n"
raised: .asciiz "^"
equals: .asciiz " = "

.text
addi $v0, $zero, 4
la $a0, calcMsg   
syscall

XValue:
addi $v0, $zero, 4
la $a0, enterX
syscall

addi $v0, $zero, 5
syscall
add $t0, $zero, $v0	#$t0 is user input for x

slt $s3, $t0, $zero	#if negative x, reprompt user
beq $s3, 1, negXValue

YValue:
addi $v0, $zero, 4
la $a0, enterY
syscall

addi $v0, $zero, 5
syscall
add $s1, $zero, $v0	#$s1 is user input for y (exponent)

add $t1, $zero, $s1	#store s1 into t1 to save for print result
addi $t5, $0, 1		

slt $s3, $t1, $zero	#if negative y, reprompt user
beq $s3, 1, negYValue

n3:
beq $t1, $zero, exit
addi $t1, $t1, -1
add $t2, $zero, $t0
add $t4, $0, $t5
addi $t5, $zero, 0

n2:
beq $t2, $zero, n3
andi $t3, $t2, 1
beq $t3, $zero, n1
add $t5, $t5, $t4

n1: 
srl $t2, $t2, 1
sll $t4, $t4, 1
j n2

negXValue:
addi $v0, $zero, 4
la $a0, negInt
syscall
j XValue

negYValue:
addi $v0, $zero, 4
la $a0, negInt
syscall
j YValue

exit: 
addi $v0, $zero, 1
add $a0, $zero, $t0
syscall

addi $v0, $zero, 4
la $a0, raised
syscall

addi $v0, $zero, 1
add $a0, $zero, $s1
syscall

addi $v0, $zero, 4
la $a0, equals
syscall

addi $v0, $zero, 1
add $a0, $zero, $t5
syscall

li $v0, 10	#end program
syscall
