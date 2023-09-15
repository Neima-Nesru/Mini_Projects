import React from 'react'
const formatter = Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'ETB',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
});
const Table = (props) => {
    return (
        <table className="result">
            <thead>
                <tr>
                    <th>Year</th>
                    <th>Total Savings</th>
                    <th>Interest (Year)</th>
                    <th>Total Interest</th>
                    <th>Invested Capital</th>
                </tr>
            </thead>
            <tbody>
                {/* loop through yearly data */}
                {props.data.map((yearData) => (
                    <tr key={yearData.year}>

                        <td>{yearData.year}</td>
                        <td>{formatter.format(yearData.savingsEndOfYear)}</td>
                        <td>{formatter.format(yearData.yearlyInterest)}</td>
                        {/* TOTAL INTEREST GAINED = saving at the end of the year - initial investment - the ongoing investment every year
                            initial investment = current saving
                             the coming investment = year *  yearly contribution

                       */}
                        <td>{formatter.format(yearData.savingsEndOfYear - props.initialInv - yearData.year * yearData.yearlyContribution)}</td>

                        {/*TOTAL INVESTED CAPITAL = initial investment + yearly contributions * year */}
                        <td>{formatter.format(props.initialInv + yearData.yearlyContribution * yearData.year)}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

export default Table