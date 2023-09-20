import React from 'react';
import { useParams } from 'react-router-dom';

const Item: React.FC = (): React.ReactNode => {

    const { itemId } = useParams();


    return (
        <div>
            <h1>item {itemId}</h1>
        </div>
    );
}

export default Item;