import {IconButton, Link, Typography, Box} from '@mui/material';

import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import InstagramIcon from '@mui/icons-material/Instagram';


export default () => {
    return (
        <div className="w-full bg-black h-8">
            <div className='flex mx-auto max-w-screen-2xl items-center justify-between'>
                <Box display="flex" alignItems="center">
                    <Typography component="div" className='fwhitespace-nowrap text-white overflow-hidden text-ellipsis text-base italic'>
                        One kebab please
                    </Typography>
                </Box>
                <Box display="flex" alignItems="center">
                    <Typography component="div" className='whitespace-nowrap text-white overflow-hidden text-ellipsis text-xs opacity-70 inline'>
                        Visit our showroom in Zalupa Street Vagina City, 1337
                    </Typography>
                    <a className='underline decoration-2 text-white hover:underline-offset-4 pl-3 hover:text-sky-500' href='#'>Contact Us</a>
                </Box>
                <Box display="flex" justifyContent="end">
                    <IconButton component={Link} href="https://github.com" className='p-0 h-7'>
                        <GitHubIcon className='text-xl text-white hover:text-sky-500' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.facebook.com" className='p-0 h-7'>
                        <FacebookIcon className='text-xl text-white hover:text-sky-500' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.instagram.com" className='p-0 h-7'>
                        <InstagramIcon className='text-xl text-white hover:text-sky-500' /> {/* Customize icon size */}
                    </IconButton>
                </Box>
            </div>
        </div>
    )
}