import React from 'react'
import './ExpenseList.css'
import ExpenseItem from './ExpenseItem'

const ExpenseList = props => {

    if (props.items.length === 0) {
        return <h2 className='expenses-list__fallback'>Found No List</h2>
    }

    return (
        <div>
            <ul className='expenses-list'>
                {
                    props.items.map(expenses =>
                        <ExpenseItem key={expenses.id}
                            title={expenses.title}
                            date={expenses.date}
                            amount={expenses.amount} />)
                }
            </ul>
        </div>
    )
}

export default ExpenseList