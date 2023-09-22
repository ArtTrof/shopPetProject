import { Typography, Link, IconButton,} from '@mui/material';

import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import InstagramIcon from '@mui/icons-material/Instagram';

export default () => {

    return (
        <div className='bg-black h-7'>
            <div className="flex mx-auto grow max-w-screen-2xl justify-between">
                <div className='p-0 h-max'>
                <IconButton component={Link} href="https://github.com" className='p-0 h-5'>
                        <GitHubIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.facebook.com" className='p-0 h-5'>
                        <FacebookIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                    <IconButton component={Link} href="https://www.instagram.com" className='p-0 h-5'>
                        <InstagramIcon className='text-xs opacity-50 text-white hover:text-sky-500 hover:opacity-100' /> {/* Customize icon size */}
                    </IconButton>
                </div>
                <div>
                    <Typography component="div" className='opacity-60 grow whitespace-nowrap text-white overflow-hidden text-ellipsis text-xs'>
                        Payment options: ...
                    </Typography>
                </div>
                <div>
                    <Typography component="div" className='opacity-60 grow whitespace-nowrap text-white overflow-hidden text-ellipsis text-xs italic'>
                        Copyright © 2023 Shop ADS. BCP.
                    </Typography>
                </div>
            </div>
        </div>
    )
};