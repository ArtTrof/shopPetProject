// @TODO guess a better name

import ItemBlock from '@/components/ItemBlock/ItemBlock';
import React from 'react';

import { BasicDemo } from '@/components/Carousel/BasicDemo';

const Main: React.FC = (): React.ReactNode => {
    return (
        <div className='grid'>
            <BasicDemo />
            <h2 className='text-2xl font-semibold mt-9 mb-3'>New Products</h2>
            <div className='grid grid-cols-6 gap-4'>
                <ItemBlock rating={4}/>
                <ItemBlock rating={3}/>
                <ItemBlock rating={5}/>
                <ItemBlock rating={4}/>
                <ItemBlock rating={2}/>
                <ItemBlock rating={4}/>

                {/* <ItemBlock rating={4}/>
                <ItemBlock rating={3}/>
                <ItemBlock rating={5}/>
                <ItemBlock rating={4}/>
                <ItemBlock rating={2}/>
                <ItemBlock rating={4}/> */}
            </div>
            <div className='grid grid-cols-6 gap-4'>
                <img src="./Desktops.jpg" alt="" />
                <ItemBlock rating={3}/>
                <ItemBlock rating={5}/>
                <ItemBlock rating={4}/>
                <ItemBlock rating={2}/>
                <ItemBlock rating={4}/>
            </div>
        </div>
    )
}

export default Main;