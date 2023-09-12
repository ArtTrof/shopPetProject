import { AppBar, Typography, Link, IconButton, Box,} from '@mui/material';
import Grid2 from '@mui/material/Unstable_Grid2/Grid2';
import { useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

export default () => {
    const [activeMenu, setActiveMenu] = useState(-1);

	const onActiveMenu = (index: any) => {
		setActiveMenu(index)
	}

    const navMenu = ['Laptops', 'Phone', 'PC', 'All Other'];

    return (
        <AppBar position="sticky" color="primary">
            <Grid2 container  display="flex" justifyContent="center">
                <Grid2 xs={10} display="flex" justifyContent="space-between" className="h-16">
                    <Box display="flex" alignItems="center">
                        <img
                            onClick={() => onActiveMenu(-1)}
                            src="data:image/png;base64,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"
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
                        <IconButton component={Link} href="#">
                            <SearchIcon className='text-black hover:text-sky-500' />
                        </IconButton>
                        <IconButton component={Link} href="#">
                            <ShoppingCartIcon className='text-black hover:text-sky-500' />
                        </IconButton>
                        <IconButton component={Link} href="#">
                            <AccountCircleIcon className='text-black hover:text-sky-500' />
                        </IconButton>
                    </Box>
                </Grid2>
            </Grid2>
        </AppBar>
    )
};