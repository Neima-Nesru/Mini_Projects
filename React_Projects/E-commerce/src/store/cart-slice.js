import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
    name: 'cart',
    initialState: {
        items: [],
        totalQuantity: 0
        // totalPrice: 0
    },
    reducers: {/* methods */
        addItem(state, action) {
            // extracting extra data
            const newItem = action.payload
            const existingItem = state.items.find(item => item.itemId === newItem.id)
            state.totalQuantity++

            if (!existingItem) {
                state.items.push({
                    itemId: newItem.id,
                    name: newItem.title,
                    price: newItem.price,
                    quantity: 1,
                    totalPrice: newItem.price
                })
            }
            else {
                existingItem.quantity++
                existingItem.totalPrice = existingItem.totalPrice + newItem.price

            }
        },
        removeItem(state, action) {
            const id = action.payload
            const existingItem = state.items.find(item => item.itemId === id)
            state.totalQuantity--

            if (existingItem.quantity === 1) {
                state.items = state.items.filter(item => item.itemId !== id)
            }
            else {
                existingItem.quantity--
                existingItem.totalPrice = existingItem.totalPrice - existingItem.price
            }
        },

    }
})

export const cartActions = cartSlice.actions
export default cartSlice