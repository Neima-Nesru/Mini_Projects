import './ExpenseItem.css'
import Card from '../UI/Card'
import ExpenseDate from './ExpenseDate';
// import React, { useState } from 'react'
const ExpenseItem = props => {
    // const expenseDate = '2023/08/15';
    // const expenseTitle = 'School Fee';
    // const expenseAmount = '2899 ETB';

    // const [title, setTitle] = useState(props.title)
    // const clickHandler = () => {
    //     setTitle('Car Rent')
    // }
    return <li>
        <Card className='expense-item'>
            <ExpenseDate date={props.date} />
            <div className='expense-item__description'>
                <h2>{props.title}</h2>
                <div className='expense-item__price'>{props.amount}</div>
            </div>
            {/* <button onClick={clickHandler}>Change Title</button> */}
        </Card>
    </li>
}

export default ExpenseItem;