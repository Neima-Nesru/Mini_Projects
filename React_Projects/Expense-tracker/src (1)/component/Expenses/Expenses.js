import ExpenseFilter from './ExpenseFilter.js';
import Card from '../UI/Card';
import ExpenseList from './ExpenseList.js';
import './Expenses.css'
import { useState } from 'react';
import ExpenseChart from './ExpenseChart.js';
const Expenses = props => {
    const [filteredYear, setFilteredYear] = useState('2020');

    const filterChangeHandler = selectedYear => {
        setFilteredYear(selectedYear);
    };
    const filteredExpense = props.items.filter(expense => {
        return expense.date.getFullYear().toString() === filteredYear;
    })

    return <Card className="expenses">
        <ExpenseFilter selected={filteredYear} onChangeFilter={filterChangeHandler} />

        <ExpenseChart expenses={filteredExpense} />
        <ExpenseList items={filteredExpense} />
        {/*  
        filteredExpense.length === 0 ? <p>No Expense this Year</p> :
            // similar work with the below commented code
            props.items.map(expenses => <ExpenseItem key={expenses.id} title={expenses.title} date={expenses.date} amount={expenses.amount} />
            )}
        {<ExpenseItem title={props.items[0].title} date={props.items[0].date} amount={props.items[0].amount} />
        <ExpenseItem title={props.items[1].title} date={props.items[1].date} amount={props.items[1].amount} />
        <ExpenseItem title={props.items[2].title} date={props.items[2].date} amount={props.items[2].amount} />
     */}

    </Card>
}
export default Expenses