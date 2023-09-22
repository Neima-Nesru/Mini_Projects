import React, { useContext, useState } from 'react'
import Modal from '../UI/Modal'
import classes from './Cart.module.css'
import CartContext from '../../store/cart-context'
import CartItem from './CartItem'
import Checkout from './Checkout'

const Cart = (props) => {
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [didSubmit, setDidSubmit] = useState(false)
    const [isCheckOut, setIsCheckOut] = useState(false)
    const cartCtx = useContext(CartContext)
    const totalAmount = `${cartCtx.totalAmount.toFixed(2)} ETB`
    const hasItems = cartCtx.items.length > 0

    const cartItemAddHandler = (item) => {
        cartCtx.addItem({ ...item, amount: 1 })
    }
    const cartItemRemoveHandler = (id) => {
        cartCtx.removeItem(id)
    }
    const orderHandler = () => {
        setIsCheckOut(true)

    }

    const submitOrderHandler = async (userData) => {
        setIsSubmitting(true)
        const url = 'https://react-http-1c6ec-default-rtdb.firebaseio.com/orders.json'

        await fetch(url, {
            method: 'POST',
            body: JSON.stringify({
                // data to send to the backend
                user: userData,
                orderedItems: cartCtx.items
            }),

        })
        setIsSubmitting(false)
        setDidSubmit(true)
        cartCtx.clearCart()
    }

    const cartItems = <ul className={classes['cart-items']}>
        {
            // transforming this items to items inthe cart
            cartCtx.items.map((item) => {
                return <li>
                    <CartItem key={item.id}
                        name={item.name}
                        amount={item.amount}
                        price={item.price}
                        onRemove={cartItemRemoveHandler.bind(null, item.id)}
                        onAdd={cartItemAddHandler.bind(null, item)}
                    />
                </li>
            })
        }
    </ul>
    const modalActions = <div className={classes.actions}>
        <button onClick={props.onClose} className={classes['button-close']}>Close</button>
        {hasItems && <button className={classes.button} onClick={orderHandler}>Order</button>
        } </div>

    const cartModalContent = <React.Fragment>
        {cartItems}
        {/* Total amount */}
        <div className={classes.total}>
            <span>Total Amount</span>
            <span>{totalAmount}</span>
        </div>
        {isCheckOut && <Checkout onCancel={props.onClose} onConfirm={submitOrderHandler} />}

        {/* actions for the cart */}
        {!isCheckOut && modalActions}
    </React.Fragment>

    const isSubmittingModalContent = <p>Sending Ordered Data...</p>

    const didSubmitModalContent = <React.Fragment>
        <p>Congrajulation! Successfully sent the order</p>

        <div className={classes.actions}>
            <button onClick={props.onClose} className={classes.button}>Ok</button>

        </div>
    </React.Fragment>
    return (
        // should be shown if 'Your cart' button is clicked
        <Modal onClose={props.onClose}>
            {!isSubmitting && !didSubmit && cartModalContent}
            {isSubmitting && isSubmittingModalContent}
            {!isSubmitting && didSubmit && didSubmitModalContent}
        </Modal>
    )
}

export default Cart