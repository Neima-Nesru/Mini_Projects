import React, { useEffect, useState } from 'react'
import classes from './AvailableMeals.module.css'
import Card from '../UI/Card'
import MealItem from './MealItem/MealItem'


const AvailableMeals = () => {
  const [meals, setMeals] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState()

  useEffect(() => {
    setIsLoading(true)
    const fetchMeals = async () => {
      const url = 'https://react-http-1c6ec-default-rtdb.firebaseio.com/meals.json'

      const response = await fetch(url)
      if (!response.ok) {
        throw new Error('Something went wrong!')
      }

      const data = await response.json()

      // transform the data
      const loadedData = []
      for (const key in data) {
        loadedData.push({
          id: key,
          name: data[key].name,
          description: data[key].description,
          price: data[key].price
        })

      }
      setMeals(loadedData)
      setIsLoading(false)
    }

    // the reason why we are not using try...catch block is useEffect can't return promise
    fetchMeals().catch(error => {
      setIsLoading(false)
      setError(error.message)
    })

  }, [])

  // check if it is loading
  if (isLoading) {
    return <p className={classes.MealsLoading}>Loading...</p>
  }

  // check if there is an error
  if (error) {
    return <p className={classes.MealsError}>{error}</p>
  }
  const mealsList = meals.map((meal) => {
    return <MealItem
      key={meal.id}
      id={meal.id}
      name={meal.name}
      description={meal.description}
      price={meal.price} />
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