# SciCalc 36

A classic-style scientific calculator inspired by traditional handheld calculator workflows.

## Basic operations

Use **AC/ON** to clear the calculator and return to the normal input state.
For example, after entering `123`, press **AC/ON** to clear the display.

Use **2nd** to select the second function of the next key you press. The second
function is shown in the upper part of each button, above the divider line. For
example, enter `2`, press **2nd**, then press **LOG** to use `10^x` and get
`100`. Pressing **2nd** a second time cancels the selected second function.

Use **3rd** to select the third function of the next key you press. The third
function is printed above the button. For example, enter `5`, press **3rd**,
then press **LOG** to use `x!` and get `120`.

## Result

Results are shown on the calculator display after an operation is completed.
For simple calculations, press **=** to evaluate the expression and show the
result. Some functions, such as trigonometric, logarithmic, statistical, or
conversion functions, display their result immediately after the corresponding
function key is pressed.

The display shows up to 10 significant digits. Very large or very small values
are shown with an exponent. In this notation, the number before the exponent is
the mantissa and the exponent indicates the power of ten. For example,
`1.23 05` means `1.23 x 10^5`.

## Basic arithmetic

Enter basic arithmetic operations in the usual order: first enter the left
number, then the operator, then the right number, and finally press **=**.
Use **+** for addition, **-** for subtraction, **x** for multiplication, and
**÷** for division.

Examples:

- `2` **+** `3` **=** gives `5`
- `8` **-** `5` **=** gives `3`
- `4` **x** `6` **=** gives `24`
- `9` **÷** `3` **=** gives `3`

Functions such as **LOG**, **LN**, and **SIN** are applied to the value that is
currently shown on the display. Enter the value first, then press the function
key.

Examples:

- `100` **LOG** gives `2`
- `1` **LN** gives `0`
- `30` **SIN** gives the sine of `30` in the currently selected angle unit

Use **(** and **)** to enter parentheses in expressions. For example, enter
**(** `2` **+** `3` **)** **x** `4` **=** to calculate `(2 + 3) x 4`.

To enter Pi, press **3rd**, then **÷**. The `Pi` function is printed above the
**÷** button.

## Percents

Press **3rd**, then **y^x** to enter **%**. The percent operation uses the
operator that was entered before it.

**Percentage:** use **x** followed by **%** to calculate a percentage of a
value. For example, `200` **x** `15` **3rd** **%** gives `30`, which is 15%
of 200.

**Ratio:** use **÷** followed by **%** to calculate what percentage one value
is of another value. For example, `30` **÷** `200` **3rd** **%** gives `15`,
because 30 is 15% of 200.

**Add-on:** use **+** followed by **%** to add a percentage to a value. For
example, `200` **+** `15` **3rd** **%** gives `230`.

**Discount:** use **-** followed by **%** to subtract a percentage from a value.
For example, `200` **-** `15` **3rd** **%** gives `170`.

## Fractions

Use **a b/c** to enter fractions. Press it once to enter the fraction bar, then
enter the denominator. For example, `1` **a b/c** `2` enters `1⌟2`, which means
`1/2`.

Press **a b/c** a second time while entering a fraction to create a mixed
number. For example, `6` **a b/c** `4` **a b/c** `6` enters `6_4⌟6`, which is
shown as `6_2⌟3` after evaluation.

Fractions can be used with the normal arithmetic operators. For example,
`1` **a b/c** `2` **+** `1` **a b/c** `3` **=** gives `5⌟6`.

Use **2nd**, then **a b/c**, to select **d/c**. This toggles a fraction between
mixed-number form and improper-fraction form. For example, `30` **a b/c** `4`
**2nd** **a b/c** changes `30⌟4` to `7_1⌟2`, pressing **2nd** **a b/c** again
changes it to `15⌟2`.

Use **3rd**, then **a b/c**, to select **F <> D**. This toggles between decimal
and fraction display. For example, `0.75` **3rd** **a b/c** shows `3⌟4`, and
pressing **3rd** **a b/c** again shows `0.75`.
