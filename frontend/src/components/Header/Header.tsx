import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Typography, Link, IconButton} from '@mui/material';

import SearchIcon from '@mui/icons-material/Search';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

export default () => {
    const navigate = useNavigate();
    const [activeMenu, setActiveMenu] = useState(-1);

	const onActiveMenu = (index: any) => {
		setActiveMenu(index)
	}

    const navMenu = ['Laptops', 'Decktop PCs', 'Phone', 'All Other'];

    return (
        <div className='w-full shadow-md'>
            <div className="flex items-center max-w-screen-2xl justify-between mx-auto h-16  container mx-auto">
                <div className='flex items-center'>
                    <img
                        onClick={() => {
                            navigate('/');
                            setActiveMenu(-1);
                        }}
                        src="/logo.png"
                        alt="Logo" className='h-11 w-auto cursor-pointer'
                    />
                    { navMenu.map((value, index) => (
                            <Typography key={index}
                                onClick={() => onActiveMenu(index)}
                                className={activeMenu === index 
                                ? 'text-sm text-white bg-sky-600 rounded-2xl font-semibold cursor-pointer py-2 px-4 !ml-4' 
                                : 'hover:text-white hover:bg-sky-600 hover:rounded-2xl text-sm text-black font-semibold cursor-pointer py-2 px-4 !ml-4 transition-all duration-300'}
                            >
                                {value}
                            </Typography>
                        )
                    )}
                </div>
                <div className='flex items-center justify-end'>
                    <IconButton component={Link}>
                        <SearchIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                    <IconButton component={Link} onClick={() => navigate('/cart')}>
                        <ShoppingCartIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                    <IconButton component={Link} onClick={() => navigate('/login')}>
                        <AccountCircleIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                </div>
            </div>
        </div>
    )
};