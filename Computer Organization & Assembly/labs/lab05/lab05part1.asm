#Kerilee Bookleiner
.data
	names: .asciiz "steve", "john", "chelsea", "julia", "ryan"
	ages: .byte 20, 25, 22, 21, 23
	enterName: .asciiz "Please enter a name: "
	age: .asciiz "Age is: "
	notFound: "Not found!"
	
	
.text
main:
	addi $v0, $zero, 4
	la $a0, enterName
	syscall
	jal _readString	
	la $t4, names		#the string
	add $s2, $zero, $v0
	jal _StrEqual
	
	
	
	
_StrEqual:
.text
add $s3, $zero, $s2	#the input
loop:
	addi $s6, $s6, 1
	beq $s6, 29, notEqual
	lb $s0, 0($t4)
	lb $s1, 0($s3)
	beq $zero, $s0, end
	bne $s0, $s1, done
	add $s7, $zero, $zero
	addi $t4, $t4, 1	#increment pointer
	addi $s3, $s3, 1
	j loop

done:
addi $s7, $zero, 1

end:
beq $s7, $zero, foundIt
addi $t4, $t4, 1
add $s3, $zero, $s2	#the input
j loop
	
notEqual:
li $v0,4
la $a0,notFound
syscall	
li $v0,10
syscall

foundIt:
li $v0,4
la $a0,age
syscall
jal _LookUpAge




_LookUpAge:
.text
beq $s6, 6, steve
beq $s6, 11, john
beq $s6, 19, chelsea
beq $s6, 25, julia
beq $s6, 29, ryan

steve:
addi $s5, $zero, 20
addi $v0, $zero, 1 
add $a0, $zero, $s0 
syscall 
li $v0,10
syscall
john:
addi $s5, $zero, 25
addi $v0, $zero, 1 
add $a0, $zero, $s5 
syscall 
li $v0,10
syscall
chelsea:
addi $s5, $zero, 22
addi $v0, $zero, 1 
add $a0, $zero, $s5
syscall 
li $v0,10
syscall
julia:
addi $s5, $zero, 21
addi $v0, $zero, 1 
add $a0, $zero, $s5 
syscall 
li $v0,10
syscall
ryan:
addi $s5, $zero, 23
addi $v0, $zero, 1 
add $a0, $zero, $s5 
syscall 
li $v0,10
syscall
	
_readString:
.data
	buffer: .space 64
.text
	la $a0, buffer	   	#use allotted space to store the string
	li $a1, 15		#max char = 15
	li $v0, 8
	syscall
	sw $a0, 4($sp) 	 	#a0 is the input string(name)
	add $v0, $zero, $a0
	jr $ra
