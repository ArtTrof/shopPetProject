import { Grid, Typography, Link, IconButton,} from '@mui/material';

import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import InstagramIcon from '@mui/icons-material/Instagram';

export default () => {

    return (
        <div className='w-full bg-black h-7'>
            <div className="container mx-auto flex items-center justify-between">
                <Grid item xs={4} className='p-0 h-max'>
                    <IconButton component={Link} href="https://github.com" className='p-0 h-5'>
                        <GitHubIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.facebook.com" className='p-0 h-5'>
                        <FacebookIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.instagram.com" className='p-0 h-5'>
                        <InstagramIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                </Grid>
                <Grid item xs={4}>
                    <Typography component="div" className='opacity-60 grow whitespace-nowrap text-white overflow-hidden text-ellipsis text-xs'>
                        Payment options: ...
                    </Typography>
                </Grid>
                <Grid item xs={4}>
                    <Typography component="div" className='opacity-60 grow whitespace-nowrap text-white overflow-hidden text-ellipsis text-xs italic'>
                        Copyright © 2023 Shop ADS. BCP.
                    </Typography>
                </Grid>
            </div>
        </div>
    )
};