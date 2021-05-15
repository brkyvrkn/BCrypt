# BCrypt

## Introduction

For data files of different lengths (eg. 1 page length, 10 page length, 100 page length and 1000 page length document), the performance of the AES algorithm is measured in terms of memory, application time, and security. The effects of the same key size on security were examined and the main asymmetric primitive under the encryption and decryption algorithms were coded.

## Scenario

1. “Euclid’s Extended Algorithm” to calculate GCD, multiplicative inverse and to check the relatively prime condition
2. FLT to test the primality of any number
3. Fast Exponentiation
4. “DH Key Exchange” for “AES or DES”
5. AES

## Briefing

In AES algorithm, input, output and matrices are 128 bits. The matrix consists of 4 rows, 4 columns (4 × 4), 16 panes. This matrix is called ‘status.. One byte of data is dropped in each pane of the state. Creates a 32-bit word on each line.

AES algorithm is a block cipher algorithm that encrypts 128 bit data blocks with 128, 192 or 256 bit key options. The difference between the key length bit numbers changes the number of AES tour cycles.

## Description

The text from the input is divided into 128-bit segments. Each piece is placed in the status matrix. After the status matrix has been created, all operations can now be performed on it. Likewise, in the 128 bit key that has already been received, this is treated as a status matrix. The status matrix in which the input text is written is first collected with the key. The first operation is the only nonlinear operation of the algorithm, the byte substitution. Each element of the state matrix is replaced by the values in the S-box that have been calculated by pre-calculating their values.

In a row shift operation, the rows are shifted cyclically, respectively. This means that the first line is not changed, the second line is shifted by 1 to the left, the third line is shifted by 2 to the left, and the last line is shifted to the left by 3. Overflow panes are added to the beginning of the stride.


>In this process, the new column is obtained using the elements of the old column. In doing so, the elements of the new column are calculated individually, taking into account each element of the old column. The calculation consists of multiplication and addition. Multiplication uses a fixed number (a (x)).

In each round, a tour key is generated together with the operations mentioned earlier and each round is subjected to the resulting situation and the new key collection prepared for that round. This is the sum of the finite fields and corresponds to the bitwise specific or operation. The 128-bit status matrix and the 128-bit intermediate key value are summed by bit-by-bit special or element.

The AES algorithm receives the key and processes it through a series of processes to generate the number of keys. This number is 10 for a 128-bit length. 10 different keys are generated, and the resulting key becomes the first key used to decrypt it. When dissolving, the same processes are used in reverse order. In key generation, each newly created new key is obtained using the previous keys.

The encrypted text created in AES algorithm can be easily solved by reverse operations and the input text can be obtained. The operations for this decoding job are reverse row shifting, reverse S box traversing, reverse column shuffling, and aggregation with the tour switch.

A change is made in the S-box where the change is applied. In order to return to the old values, the values of the S-box are calculated by a different method. The resulting box; the normal value in the S-box is that it is arranged to give us back the initial value when we apply it to the input again.

If we call the columns w0, w1, w2, w3 from the beginning; we first add w2 and w3 with special or with we get the new w0 value. Similarly, the result of w1 and w2 gives the new w1 value. In this way, the new state is created by going backwards. Finally, after shifting the newly formed column w0 to the left 1, we pass each column through the S-box and multiply by the inverse Rc coefficient. This creates the new key.