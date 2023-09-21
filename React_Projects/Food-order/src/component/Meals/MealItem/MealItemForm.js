import React, { useRef, useState } from 'react'
import classes from './MealItemForm.module.css'
import Input from '../../UI/Input'

const MealItemForm = (props) => {
    const [amountIsValid, setAmountIsValid] = useState(true)
    const amountInputRef = useRef()
    const submitHandler = event => {
        event.preventDefault();

        const enteredAmount = amountInputRef.current.value
        const enteredAmountInNumber = +enteredAmount

        if (enteredAmount.trim().length === 0 || enteredAmountInNumber < 1 || enteredAmountInNumber > 5) {
            setAmountIsValid(false)
            return

        }
        props.onAddToCart(enteredAmountInNumber)
    }
    return (

        <form onSubmit={submitHandler} className={classes.form}>
            <Input
                ref={amountInputRef}
                label='Amount' input={{
                    id: 'amount',
                    type: 'number',
                    min: '1',
                    max: '5',
                    step: '1',
                    defaultValue: '1'
                }}
            />
            <button>+ Add</button>
            {!amountIsValid && <p style={{ color: 'red' }}>please, enter valid input!</p>}

        </form>

    )
}

export default MealItemForm