import React, { useState } from 'react'
import './ExpenseForm.css'
const ExpenseForm = (props) => {
    const [newTitle, setNewTitle] = useState('')
    const [newDate, setNewDate] = useState('')
    const [newAmount, setNewAmount] = useState('')

    const titleChanger = (event) => {
        setNewTitle(event.target.value)
    }

    const dateChanger = (event) => {
        setNewDate(event.target.value)
    }

    const amountChanger = (event) => {
        setNewAmount(event.target.value)
    }

    // using only one change Handler instead of 3 functions

    // const changeHandler = (identifier, value) => {
    //     if (identifier === 'title') {
    //         setNewTitle(value)
    //     }
    //     else if (identifier === 'date') {
    //         setNewDate(value)
    //     }
    //     else {
    //         setNewAmount(value)
    //     }
    // }

    const submitHandler = (event) => {
        event.preventDefault();
        const expenseData = {
            title: newTitle,
            date: new Date(newDate),
            amount: newAmount
        }
        props.onSaveExpenseData(expenseData)
        setNewTitle('')
        setNewDate('')
        setNewAmount('')
    }
    return (
        <form onSubmit={submitHandler}>
            <div className='new-expense__controls'>
                <div className='new-expense__control'>
                    <label>Title</label> <input type='text' value={newTitle} onChange={titleChanger} />
                    {/* <input type='text'  onChange={(event) => changeHandler('title', event.target.value)} /> */}
                </div>

                <div className='new-expense__control'>
                    <label>Date</label><input type='date' value={newDate} min='2019-01-01' max='2023-08-14' onChange={dateChanger} />
                    {/* <input type='text' onChange={(event) => changeHandler('title', event.target.value)} /> */}
                </div>

                <div className='new-expense__control'>
                    <label>Amount</label><input type='number' value={newAmount} min='0.01' step='0.01' onChange={amountChanger} />
                    {/* <input type='text'  onChange={(event) => changeHandler('title', event.target.value)} /> */}
                </div>

            </div>
            <div className='new-expense__actions'>
                <button type='submit'>Add Expense</button>
            </div>
        </form>
    )
}

export default ExpenseForm