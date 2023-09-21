import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AppBar, Typography, Link, IconButton, Box,} from '@mui/material';

import SearchIcon from '@mui/icons-material/Search';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

export default () => {
    const navigate = useNavigate();
    const [activeMenu, setActiveMenu] = useState(-1);

	const onActiveMenu = (index: any) => {
		setActiveMenu(index)
	}

    const navMenu = ['Laptops', 'Phone', 'PC', 'All Other'];

    return (
        <AppBar position="sticky" color="primary" className='!w-full'>
            <div className="container mx-auto flex items-center justify-between h-16">
                <Box display="flex" alignItems="center">
                    <img
                        onClick={() => navigate('/')}
                        src="/logo.png"
                        alt="Logo" className='h-11 w-auto cursor-pointer'
                    />
                    { navMenu.map((value, index) => (
                            <Typography key={index}
                                onClick={() => onActiveMenu(index)}
                                className={activeMenu === index ? 'text-sky-500 underline cursor-pointer !ml-4' : 'text-black hover:text-sky-500 cursor-pointer !ml-4'}>{value}
                            </Typography>
                        )
                    )}
                </Box>
                <Box display="flex"  className='items-center justify-end'>
                    <IconButton component={Link}>
                        <SearchIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                    <IconButton component={Link} onClick={() => navigate('/cart')}>
                        <ShoppingCartIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                    <IconButton component={Link} onClick={() => navigate('/login')}>
                        <AccountCircleIcon className='text-black hover:text-sky-500' />
                    </IconButton>
                </Box>
            </div>
        </AppBar>
    )
};