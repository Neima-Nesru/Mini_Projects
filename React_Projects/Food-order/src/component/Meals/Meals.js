import React from 'react'
import MealsSummary from './MealsSummary'
import AvailableMeals from './AvailableMeals'
import Card from '../UI/Card'

const Meals = () => {
    return (
        <React.Fragment>
            <MealsSummary />
            <AvailableMeals />
        </React.Fragment>
    )
}

export default Meals