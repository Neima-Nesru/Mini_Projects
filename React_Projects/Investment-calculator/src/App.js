import Header from './components/Header';
import Form from './components/Form';
import Table from './components/Table';
import { useState } from 'react';
function App() {
    let [userInput, setUserInput] = useState(null)

    const calculateHandler = (userInput) => {
        // do something with yearlyData ...
        setUserInput(userInput)
    };
    // this data will be drived states from userInputs
    const yearlyData = []; // per-year results

    if (userInput) {
        let currentSavings = +userInput['current-savings']; // feel free to change the shape of this input object!
        const yearlyContribution = +userInput['yearly-contribution']; // as mentioned: feel free to change the shape...
        const expectedReturn = +userInput['expected-return'] / 100;
        const duration = +userInput['duration'];

        // The below code calculates yearly results (total savings, interest etc)
        for (let i = 0; i < duration; i++) {
            const yearlyInterest = currentSavings * expectedReturn;
            currentSavings += yearlyInterest + yearlyContribution;
            yearlyData.push({
                // feel free to change the shape of the data pushed to the array!
                year: i + 1,
                yearlyInterest: yearlyInterest,
                savingsEndOfYear: currentSavings,
                yearlyContribution: yearlyContribution,
            });
        }
    }
    return (
        <div>
            <Header />
            {/* <Form onCalculate={(event) => { calculateHandler(userInput) }} /> */}
            <Form onCalculate={calculateHandler} />

            {/* Todo: Show below table conditionally (only once result data is available) */}
            {userInput && <Table data={yearlyData} initialInv={userInput['current-savings']} />}

            {/* Show fallback text if no data is available */}
            {!userInput && <p style={{ color: 'red', textAlign: 'center' }}>Oops! No Investment calculated!</p>}

        </div>
    );

}

export default App;