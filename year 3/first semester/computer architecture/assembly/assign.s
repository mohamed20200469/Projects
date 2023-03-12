.intel_syntax noprefix

.section .data        # memory variables

input: .asciz "%d"                    # string terminated by 0 that will be used for scanf parameter
output: .asciz "The sum is: %f\n"     # string terminated by 0 that will be used for printf parameter

n: .int 0             # the variable n which we will get from user using scanf
sum: .double 0.0      # the variable s=1/1+1/2+1/3+...+1/n that will be calculated by the program and will be printed by printf, s is initialized to 0
one: .double 1.0      # variable which always carries 1
num1: .double 1.0     # variable which will carry the denominator in 1/n in the loop
num2: .double 1.0     # variable which will carry the n in the loop


.section .text        # instructions
.globl _main          # make _main accessible from external

_main:                # the label indicating the start of the program
   push OFFSET n      # push to stack the second parameter to scanf (the address of the integer variable n)
   push OFFSET input  # push to stack the first parameter to scanf
   call _scanf        # call scanf, it will use the two parameters on the top of the stack in the reverse order
   add esp, 8         # pop the above two parameters from the stack (the esp register keeps track of the stack top, 8=2*4 bytes popped as param was 4 bytes)
   
   mov ecx, n         # ecx <- n (the number of iterations)
loop1:
   # the following 4 instructions increase sum by 1/num1
   fld qword ptr one              # push 1 to the floating point stack
   fdiv qword ptr num1            # pop the floating point stack top (1), divide it over num1 and push the result (1/num1)

   fadd qword ptr sum             # pop the floating point stack top (1/num1), add it to sum, and push the result (sum+(1/num1))
   fstp qword ptr sum             # pop the floating point stack top (sum+(1/num1)) into the memory variable sum
   
   # the following 3 instructions are to increase sum by num2
   fld qword ptr num2             # push num2 to the floating point stack

   fadd qword ptr sum             # pop the floating point stack top num2, add it to sum, and push the result (sum+num2)
   fstp qword ptr sum             # pop the floating point stack top (sum+num2) into the memory variable sum

   # the following 6 instructions increase num1 and num2 by 1   
   fld qword ptr num1              # push 1 to the floating point stack
   fadd qword ptr one              # pop the floating point stack top (1), add it to num1 and push the result (num1+1)
   fstp qword ptr num1             # pop the floating point stack top (r+1) into the memory variable num1
   
   fld qword ptr num2              # push 1 to the floating point stack
   fadd qword ptr one              # pop the floating point stack top (1), add it to num2 and push the result (num2+1)
   fstp qword ptr num2             # pop the floating point stack top (num2+1) into the memory variable num2

   loop loop1         # ecx -=1 , then goto loop1 only if ecx is not zero
   
   push [sum+4]         # push to stack the high 32-bits of the second parameter to printf (the double at label s)
   push sum             # push to stack the low 32-bits of the second parameter to printf (the double at label s)
   push OFFSET output # push to stack the first parameter to printf
   call _printf       # call printf
   add esp, 12        # pop the three parameters

   ret                # end the main function
