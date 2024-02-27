import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const BookingList = () => {

    const [bookings, setBooking] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const bookDate  = new Date().toLocaleDateString('en-CA');

        fetch(`api/bookings/${bookDate}`)
            .then(response => response.json())
            .then(data => {
                setBooking(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/api/book/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBookings = [...bookings].filter(i => i.id !== id);
            setBooking(updatedBookings);
        });
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const BookingList = bookings.map(booking => {
        return <tr key={booking.id}>
            <td style={{whiteSpace: 'nowrap'}}>{booking.customerName}</td>
            <td>{booking.tableSize}</td>
            <td>{booking.bookDate}</td>
            <td>{booking.bookTime}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/book/" + booking.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(booking.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/book/new">Add booking</Button>
                </div>
                <h3>Restaurant Booking List</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="25%">Customer Name</th>
                        <th width="5%">Table Size</th>
                        <th width="20%">BookDate</th>
                        <th width="5%">BookTime</th>
                        <th width="15%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {BookingList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default BookingList;