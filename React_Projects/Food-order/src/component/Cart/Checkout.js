import React, { useRef, useState } from 'react'
import classes from './Checkout.module.css'

const isEmpty = (value) => value.trim() === ''
const isFiveChars = (value) => value.trim().length === 5

const Checkout = (props) => {
    const [formValidity, setFormValidity] = useState({
        name: true,
        street: true,
        postal: true,
        city: true
    })
    const nameInputRef = useRef()
    const streetInputRef = useRef()
    const postalInputRef = useRef()
    const cityInputRef = useRef()
    const confirmHandler = (event) => {
        event.preventDefault()

        // extract the user input
        const enteredName = nameInputRef.current.value
        const enteredStreet = streetInputRef.current.value
        const enteredPostal = postalInputRef.current.value
        const enteredCity = cityInputRef.current.value

        // validate the user input all inputs mustnot be empty & postal code should cosist of 5 digit 
        const nameIsValid = !isEmpty(enteredName)
        const streetIsValid = !isEmpty(enteredStreet)
        const postalIsValid = !isEmpty(enteredPostal) && isFiveChars(enteredPostal)
        const cityIsValid = !isEmpty(enteredCity)

        const formIsValid = nameIsValid && streetIsValid && postalIsValid && cityIsValid

        // update the state to give feedback
        setFormValidity({
            name: nameIsValid,
            street: streetIsValid,
            postal: postalIsValid,
            city: cityIsValid
        })

        if (!formIsValid) {
            // show error
            return
        }

        // submit cart
        props.onConfirm({
            name: enteredName,
            street: enteredStreet,
            postal: enteredPostal,
            city: enteredCity
        })
    }
    return (
        <form onSubmit={confirmHandler} className={classes.form}>
            <div className={`${classes.control} ${formValidity.name ? '' : classes.invalid}`}>
                <label htmlFor='name'>
                    Your Name
                </label>
                <input id='name' type='text' ref={nameInputRef} />
                {!formValidity.name && <p>Please, enter valid name!</p>}
            </div>

            <div className={`${classes.control} ${formValidity.street ? '' : classes.invalid}`}>
                <label htmlFor='street'>
                    Street
                </label>
                <input id='street' type='text' ref={streetInputRef} />
                {!formValidity.street && <p>Please, enter valid street!</p>}

            </div>

            <div className={`${classes.control} ${formValidity.postal ? '' : classes.invalid}`}>
                <label htmlFor='postal'>
                    Postal Code
                </label>

                <input id='postal' type='text' ref={postalInputRef} />
                {!formValidity.postal && <p>Please, enter valid postal code!</p>}

            </div>

            <div className={`${classes.control} ${formValidity.city ? '' : classes.invalid}`}>
                <label htmlFor='city'>
                    City
                </label>
                <input id='city' type='text' ref={cityInputRef} />
                {!formValidity.city && <p>Please, enter valid city!</p>}

            </div>

            <div className={classes.actions}>
                <button type='button' onClick={props.onCancel}>Cancel</button>
                <button className={classes.submit}>Confirm</button>
            </div>
        </form>

    )
}

export default Checkout