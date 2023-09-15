import React, { useState } from 'react'
const initial = {
    'current-savings': 1000,
    'yearly-contribution': 1200,
    'expected-return': 7,
    'duration': 10
}
const Form = (props) => {

    // initializing the state
    const [userInput, setUserInput] = useState(initial)
    const resetHandler = () => {

        // resetting the state
        setUserInput(initial)

    }

    const submitHandler = (event) => {
        event.preventDefault()

        props.onCalculate(userInput)

    }
    const changeHandler = (eventSource, valueEntered) => {
        setUserInput((prevInput) => {
            return {
                ...prevInput, //copying the previous state
                [eventSource]: valueEntered //overwrite the value 
            }
        })
    }
    return (

        <form className="form" onSubmit={submitHandler}>
            <div className="input-group">
                <p>
                    <label htmlFor="current-savings">Current Savings ($)</label>
                    {/* two way binding */}
                    <input type="number" id="current-savings" value={userInput['current-savings']} onChange={(event) => changeHandler('current-savings', event.target.value)} />
                </p>
                <p>
                    <label htmlFor="yearly-contribution">Yearly Savings ($)</label>
                    <input type="number" id="yearly-contribution" value={userInput['yearly-contribution']} onChange={(event) => changeHandler('yearly-contribution', event.target.value)} />
                </p>
            </div>
            <div className="input-group">
                <p>
                    <label htmlFor="expected-return">
                        Expected Interest (%, per year)
                    </label>
                    <input type="number" id="expected-return" value={userInput['expected-return']} onChange={(event) => changeHandler('expected-return', event.target.value)} />
                </p>
                <p>
                    <label htmlFor="duration">Investment Duration (years)</label>
                    <input type="number" id="duration" value={userInput['duration']} onChange={(event) => changeHandler('duration', event.target.value)} />
                </p>
            </div>
            <p className="actions">
                <button type="reset" className="buttonAlt" onClick={resetHandler}>
                    Reset
                </button>
                <button type="submit" className="button" >
                    Calculate
                </button>
            </p>
        </form>
    )
}

export default Form