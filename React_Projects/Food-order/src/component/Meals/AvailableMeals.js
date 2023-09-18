import React from 'react'
import classes from './AvailableMeals.module.css'
import Card from '../UI/Card'
import MealItem from './MealItem/MealItem'

const DUMMY_MEALS = [
  {
    id: 'm1',
    name: 'Ktfo',
    description: 'Ethiopian traditional food',
    price: 150,
  },
  {
    id: 'm2',
    name: 'Shro',
    description: 'Ethiopian casual food',
    price: 50,
  },
  {
    id: 'm1',
    name: 'Gomen',
    description: 'Ethiopian Normal food',
    price: 90,
  },


]
const AvailableMeals = () => {
  const mealsList = DUMMY_MEALS.map((meals) => {
    return <MealItem
      key={meals.id}
      id={meals.id}
      name={meals.name}
      description={meals.description}
      price={meals.price} />
  })
  return (
    <section className={classes.meals}>
      <Card>
        <ul>
          {mealsList}
        </ul>
      </Card>

    </section>
  )
}

export default AvailableMeals