	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p0_m2p0_a2p0_c2p0"
	.file	"builtin.c"
	.globl	print
	.p2align	2
	.type	print,@function
print:
	.cfi_startproc
	mv	a1, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	tail	printf@plt
.Lfunc_end0:
	.size	print, .Lfunc_end0-print
	.cfi_endproc

	.globl	println
	.p2align	2
	.type	println,@function
println:
	.cfi_startproc
	tail	puts@plt
.Lfunc_end1:
	.size	println, .Lfunc_end1-println
	.cfi_endproc

	.globl	printInt
	.p2align	2
	.type	printInt,@function
printInt:
	.cfi_startproc
	mv	a1, a0
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	tail	printf@plt
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt
	.cfi_endproc

	.globl	printlnInt
	.p2align	2
	.type	printlnInt,@function
printlnInt:
	.cfi_startproc
	mv	a1, a0
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	tail	printf@plt
.Lfunc_end3:
	.size	printlnInt, .Lfunc_end3-printlnInt
	.cfi_endproc

	.globl	getInt
	.p2align	2
	.type	getInt,@function
getInt:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf@plt
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end4:
	.size	getInt, .Lfunc_end4-getInt
	.cfi_endproc

	.globl	getString
	.p2align	2
	.type	getString,@function
getString:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	li	a0, 256
	call	malloc@plt
	mv	a1, a0
	sw	a1, 8(sp)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	scanf@plt
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	getString, .Lfunc_end5-getString
	.cfi_endproc

	.globl	toString
	.p2align	2
	.type	toString,@function
toString:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	sw	a0, 4(sp)
	li	a0, 16
	call	malloc@plt
	lw	a2, 4(sp)
	sw	a0, 8(sp)
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	call	sprintf@plt
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	toString, .Lfunc_end6-toString
	.cfi_endproc

	.globl	string.parseInt
	.p2align	2
	.type	string.parseInt,@function
string.parseInt:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	addi	a2, sp, 8
	call	sscanf@plt
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	string.parseInt, .Lfunc_end7-string.parseInt
	.cfi_endproc

	.globl	string.ord
	.p2align	2
	.type	string.ord,@function
string.ord:
	.cfi_startproc
	add	a0, a0, a1
	lb	a0, 0(a0)
	ret
.Lfunc_end8:
	.size	string.ord, .Lfunc_end8-string.ord
	.cfi_endproc

	.globl	string.length
	.p2align	2
	.type	string.length,@function
string.length:
	.cfi_startproc
	tail	strlen@plt
.Lfunc_end9:
	.size	string.length, .Lfunc_end9-string.length
	.cfi_endproc

	.globl	string.substring
	.p2align	2
	.type	string.substring,@function
string.substring:
	.cfi_startproc
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	.cfi_offset ra, -4
	sw	a1, 12(sp)
	sw	a0, 16(sp)
	sub	a0, a2, a1
	sw	a0, 20(sp)
	addi	a0, a0, 1
	call	malloc@plt
	lw	a3, 12(sp)
	lw	a1, 16(sp)
	lw	a2, 20(sp)
	sw	a0, 24(sp)
	add	a1, a1, a3
	call	memcpy@plt
	lw	a1, 20(sp)
	lw	a0, 24(sp)
	add	a2, a0, a1
	li	a1, 0
	sb	a1, 0(a2)
	lw	ra, 28(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end10:
	.size	string.substring, .Lfunc_end10-string.substring
	.cfi_endproc

	.globl	string.add
	.p2align	2
	.type	string.add,@function
string.add:
	.cfi_startproc
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	.cfi_offset ra, -4
	sw	a1, 12(sp)
	sw	a0, 4(sp)
	call	strlen@plt
	mv	a1, a0
	lw	a0, 12(sp)
	sw	a1, 8(sp)
	call	strlen@plt
	lw	a1, 8(sp)
	sw	a0, 16(sp)
	add	a0, a0, a1
	sw	a0, 20(sp)
	addi	a0, a0, 1
	call	malloc@plt
	lw	a1, 4(sp)
	lw	a2, 8(sp)
	sw	a0, 24(sp)
	call	memcpy@plt
	lw	a3, 8(sp)
	lw	a1, 12(sp)
	lw	a2, 16(sp)
	lw	a0, 24(sp)
	add	a0, a0, a3
	call	memcpy@plt
	lw	a1, 20(sp)
	lw	a0, 24(sp)
	add	a2, a0, a1
	li	a1, 0
	sb	a1, 0(a2)
	lw	ra, 28(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end11:
	.size	string.add, .Lfunc_end11-string.add
	.cfi_endproc

	.globl	string.lt
	.p2align	2
	.type	string.lt,@function
string.lt:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	srli	a0, a0, 31
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	string.lt, .Lfunc_end12-string.lt
	.cfi_endproc

	.globl	string.gt
	.p2align	2
	.type	string.gt,@function
string.gt:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	mv	a1, a0
	li	a0, 0
	slt	a0, a0, a1
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end13:
	.size	string.gt, .Lfunc_end13-string.gt
	.cfi_endproc

	.globl	string.le
	.p2align	2
	.type	string.le,@function
string.le:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	slti	a0, a0, 1
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end14:
	.size	string.le, .Lfunc_end14-string.le
	.cfi_endproc

	.globl	string.ge
	.p2align	2
	.type	string.ge,@function
string.ge:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end15:
	.size	string.ge, .Lfunc_end15-string.ge
	.cfi_endproc

	.globl	string.eq
	.p2align	2
	.type	string.eq,@function
string.eq:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	seqz	a0, a0
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end16:
	.size	string.eq, .Lfunc_end16-string.eq
	.cfi_endproc

	.globl	string.ne
	.p2align	2
	.type	string.ne,@function
string.ne:
	.cfi_startproc
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp@plt
	snez	a0, a0
	lw	ra, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end17:
	.size	string.ne, .Lfunc_end17-string.ne
	.cfi_endproc

	.type	.L.str,@object
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.2,@object
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.ident	"Ubuntu clang version 15.0.7"
	.section	".note.GNU-stack","",@progbits
	.addrsig
