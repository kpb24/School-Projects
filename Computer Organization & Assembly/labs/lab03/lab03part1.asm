#Kerilee Bookleiner
.data
promptMsg: .asciiz "Please enter your integer: "
outputMsg: .asciiz "Here is the output: "


.text
addi $v0, $zero, 4	#prompt user for input
la $a0, promptMsg
syscall

addi $v0, $zero, 5	#read input
syscall
add $t0, $zero, $v0

srl $t1, $t0, 15	
andi $t1, $t1, 7

addi $v0, $zero, 4	
la $a0, outputMsg
syscall	

addi $v0, $zero, 1
add $a0, $zero, $t1
syscall