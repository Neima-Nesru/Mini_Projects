import React, { createContext, useContext } from 'react'
import Modal from '../UI/Modal'
import classes from './Cart.module.css'
import CartContext from '../../store/cart-context'
import CartItem from './CartItem'

const Cart = (props) => {
    const cartCtx = useContext(CartContext)
    const totalAmount = `${cartCtx.totalAmount.toFixed(2)} ETB`
    const hasItems = cartCtx.items.length > 0

    const cartItemAddHandler = (item) => { }
    const cartItemRemoveHandler = (id) => { }

    const cartItems = <ul className={classes['cart-items']}>
        {
            cartCtx.items.map((item) => {
                return <li>
                    <CartItem key={item.id}
                        name={item.name}
                        amount={item.amount}
                        price={item.price}
                        onRemove={cartItemAddHandler.bind(null, item.id)}
                        onAdd={cartItemRemoveHandler.bind(item)}
                    />
                </li>
            })
        }
    </ul>

    return (

        // should be shown if 'Your cart' button is clicked
        <Modal onClose={props.onClose}>
            {cartItems}
            {/* Total amount */}
            <div className={classes.total}>
                <span>Total Amount</span>
                <span>{totalAmount}</span>
            </div>

            {/* actions for the cart */}
            <div className={classes.actions}>
                <button onClick={props.onClose} className={classes['button-close']}>Close</button>
                {hasItems && <button className={classes.button}>Order</button>
                } </div>
        </Modal>
    )
}

export default Cart