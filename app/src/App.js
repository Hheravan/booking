import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import BookingList from './BookingList';
import BookingEdit from './BookingEdit';
const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path='/bookings' exact={true} element={<BookingList/>}/>
                <Route path="/Book/:id" exact={true} element={<BookingEdit/>}/>
            </Routes>
        </Router>
    )
}

export default App;
