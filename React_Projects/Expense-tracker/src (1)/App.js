import Expenses from './component/Expenses/Expenses';
import React, { useState } from 'react'
import NewExpense from './component/NewExpense/NewExpense';
const DUMMY_EXPENSES = [
  {
    id: 'e01',
    title: 'School Fee',
    date: new Date(2023, 1, 1),
    amount: '4893'
  }
  , {
    id: 'e02',
    title: 'Transport',
    date: new Date(2023, 10, 21),
    amount: '893'
  }
  , {
    id: 'e03',
    title: 'House Rent',
    date: new Date(2022, 2, 7),
    amount: '9893'
  }
];
const App = () => {
  const [expenses, setExpenses] = useState(DUMMY_EXPENSES)

  // alternative way to jsx
  // return React.createElement('div', {}, React.createElement('h1', {}, 'Expense Tracker'), React.createElement(Expenses, { items: expenses }))
  const addExpenseHandler = (expense) => {
    setExpenses((prevExpenses) => {

      return [expense, ...prevExpenses];
    });

  };

  return (
    <div>
      <h1>Expense Tracker</h1>
      <NewExpense onAddExpense={addExpenseHandler} />
      <Expenses items={expenses} />

    </div>
  );
}

export default App;
