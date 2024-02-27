import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const BookingEdit = () => {
    const initialFormState = {
        customerName: '',
        tableSize: '',
        bookDate: '',
        bookTime: ''
    };
    const [book, setBook] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/api/book/${id}`)
                .then(response => response.json())
                .then(data => setBook(data));
        }
    }, [id, setBook]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setBook({ ...book, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch(`/api/book${book.id ? `/${book.id}` : ''}`, {
            method: (book.id) ? 'PUT' : 'POST',
            headers: {
                'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book),
            credentials: 'include'
        });
        setBook(initialFormState);
        navigate('/bookings');
    }

    const title = <h2>{book.id ? 'Edit book' : 'Add book'}</h2>;

    return (<div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="customerName">Customer Name</Label>
                        <Input type="text" name="customerName" id="customerName" value={book.customerName || ''}
                               onChange={handleChange} autoComplete="customerName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="tableSize">Table Size</Label>
                        <Input type="text" name="tableSize" id="tableSize" value={book.tableSize || ''}
                               onChange={handleChange} autoComplete="tableSize-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="bookDate">Book Date</Label>
                        <Input type="text" name="bookDate" id="bookDate" value={book.bookDate || ''}
                               onChange={handleChange} autoComplete="bookDate-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="bookTime">Book Time</Label>
                        <Input type="text" name="bookTime" id="bookTime" value={book.bookTime || ''}
                               onChange={handleChange} autoComplete="bookTime-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/bookings">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    )
};

export default BookingEdit;