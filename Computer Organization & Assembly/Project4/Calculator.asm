#Kerilee Bookleiner
.text
waitFirst:
	beq $t9, $zero, waitFirst
	add $s0, $zero, $t9
	andi $s0, $s0, 15
	beq $s0, 15, clear
	bne $s0, -1, firstNum
	j waitFirst
firstNum:
	beq $s0, 10, setOperator1
	beq $s0, 11, setOperator1
	beq $s0, 12, setOperator1	
	beq $s0, 13, setOperator1	
	add $t2, $t2, 1			#counter
	bne $t2, 1, mult101
	add $t5, $zero, $s0		#t5 is first number entered
	add $t8, $zero, $t5		#$s0 is the value input
	add $t9, $zero, $zero
	j waitFirst
mult101:
	sll $t0, $t5, 1			#multiply by 10
	sll $t1, $t5, 3
	add $t4, $t0, $t1		#t4 contains multiplied number
	add $t5, $t4, $s0		#add next input to it
	add $t8, $zero, $t5		#t5 is the first number
	add $t9, $zero, $zero
	j waitFirst
setOperator1:
	add $s3, $zero, $s0		#set the operator
	add $t9, $zero, $zero		
	j waitSecond																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
waitSecond:
	beq $t9, $zero, waitSecond
	add $s2, $zero, $t9
	andi $s2, $s2, 15
	beq $s2, 10, runOp
	beq $s2, 11, runOp
	beq $s2, 12, runOp	
	beq $s2, 13, runOp
	beq $s2, 15, clear
	beq $s2, 14, runOp
	bne $s2, -1, setOperator
	j waitSecond																																			
setOperator:
	add $s5, $zero, 1
	add $t2, $zero, $zero		#reset
	add $t0, $zero, $zero		#reset
	add $t1, $zero, $zero
	add $t4, $zero, $zero
	add $t3, $t3, 1			#counter
	bne $t3, 1, mult102
	add $t6, $zero, $s2		#s2 is second number entered
	add $t8, $zero, $t6		#t6 is the second number
	add $t9, $zero, $zero
	j waitSecond
setOperator3:
	add $s3, $zero, $s2
	add $t9, $zero, $zero
	j waitSecond
mult102:
	sll $t0, $t6, 1			#multiply by 10
	sll $t1, $t6, 3
	add $t4, $t0, $t1	
	add $t6, $t4, $s2		#t6 is total second
	add $t8, $zero, $t6
	add $t9, $zero, $zero
	j waitSecond																																		
runOp:
	bne $s5, 1, setOperator3
	add $s7, $zero, $s2		#s7 is stored op to use next time
	beq $s3, 10, addNumbers
	beq $s3, 11, subNumbers
	beq $s3, 12, multNumbers	
	beq $s3, 13, divNumbers
newOp:
add $s5, $zero, $zero
 	beq $s7, 14, equals
	add $t5, $zero, $t7 		#set first number to be running total
	add $s3, $zero, $s7
	add $s7, $zero, $zero
	add $t3, $zero, $zero
	j waitSecond											
newOp2:
	add $t5, $zero, $t7 		#set first number to be running total
	add $t9, $zero, $zero
	j waitFirst																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
####operations
addNumbers:
	add $s5, $t5, $t6
	add $t7, $zero, $s5
	add $t8, $zero, $t7		#display answer for addition
	add $t9, $zero, $zero
	j newOp	
subNumbers:
	sub $s5, $t5, $t6
	add $t7, $zero, $s5
	add $t8, $zero, $t7		#display answer for addition
	add $t9, $zero, $zero
	j newOp	
multNumbers:
	slt $s0, $t7, $zero
	beq $s0, 1, multNegative
	add $t4, $zero, $zero 		#t4 is result
	add $s6, $zero, $zero		#t5 is counter
mLoop:
	beq $s6, $t5, exit
	add $t4, $t4, $t6
	addi $s6, $s6, 1		#counter++
	j mLoop
exit:
	add $t7, $zero, $t4
	add $t8, $zero, $t7		#display answer for addition ($t4 is answer)
	add $t9, $zero, $zero
	j newOp
multNegative:
	add $t4, $zero, $zero 		#t4 is result
	add $s6, $zero, $zero		#t5 is counter
nLoop:
	beq $s6, $t5, exitNeg
	add $t4, $t4, $t6
	addi $s6, $s6, -1		#counter++
	j nLoop
exitNeg:
	sub $t7, $zero, $t4
	add $t8, $zero, $t7		#display answer 
	add $t9, $zero, $zero
	j newOp	
divNumbers:
	slt $s0, $t5, $zero
	beq $s0, 1, divNegative
	add $t4, $zero, $zero		#t4 is counter and result
dLoop:
	slt $s0, $t5 ,$t6
	beq $s0, 1, exit2
	sub $t5, $t5, $t6
	add $t8, $zero, $t5
	addi $t4, $t4, 1		#counter++
	j dLoop
exit2:
	add $t7, $zero, $t4
	add $t8, $zero, $t7		#display answer
	add $t9, $zero, $zero
	j newOp
divNegative:				#divide using negated number 
	sub $t5, $zero, $t5
	add $t4, $zero, $zero		#t4 is counter and result
dLoop2:
	slt $s0, $t5 ,$t6
	beq $s0, 1, exit3
	sub $t5, $t5, $t6
	add $t8, $zero, $t5
	addi $t4, $t4, 1		#counter++
	j dLoop2
exit3:
	sub $t7, $zero, $t4		#put number back into negative
	add $t8, $zero, $t7		#display answer
	add $t9, $zero, $zero
	j newOp
equals:
	add $t8, $zero, $t7		#display answer
	add $t3, $zero, $zero
	j newOp2
clear:
	add $t8, $zero, $zero		#reset registers to zero and start over
	add $t0, $zero, $zero	
	add $t1, $zero, $zero
	add $t2, $zero, $zero
	add $t3, $zero, $zero
	add $t4, $zero, $zero
	add $t5, $zero, $zero
	add $t6, $zero, $zero
	add $t7, $zero, $zero
	add $t9, $zero, $zero
	add $s0, $zero, $zero
	add $s1, $zero, $zero
	add $s2, $zero, $zero
	add $s3, $zero, $zero
	add $s4, $zero, $zero
	add $s5, $zero, $zero
	add $s6, $zero, $zero
	add $s7, $zero, $zero
	j waitFirst
