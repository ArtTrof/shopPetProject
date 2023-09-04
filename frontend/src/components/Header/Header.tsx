import * as MUI from '@mui/material'
// @ts-ignore
import PersonIcon from '@mui/icons-material/Person';
import PersonOutlineIcon from '@mui/icons-material/PersonOutline';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
// @ts-ignore
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import SearchIcon from '@mui/icons-material/Search';

export default () => {
    const a = ['About Us', 'Contacts'];

    return (
        <MUI.Grid container columnSpacing={2} justifyContent="center" className="bg-black">
            <MUI.Grid xs={7} item={true} className="bg-black">
                <MUI.Stack direction="row" alignItems="flex-start" justifyContent="flex-start">
                    {a.map(t =><div className='text-white p-3 cursor-pointer hover:bg-white hover:text-black text-xl' key={t}>{t}</div>)}
                </MUI.Stack>
            </MUI.Grid>
            <MUI.Grid xs={3} item={true} className="bg-black">
                <MUI.Stack direction="row" alignItems="flex-start" justifyContent="flex-end">
                    <MUI.IconButton  className="p-2">
                        <SearchIcon fontSize="large" className="text-white"/>
                    </MUI.IconButton>
                    <MUI.IconButton  className="p-2">
                        <ShoppingCartOutlinedIcon fontSize="large" className="text-white"/>
                    </MUI.IconButton>
                    <MUI.IconButton  className="p-2">
                        <PersonOutlineIcon fontSize="large" className="text-white"/>
                    </MUI.IconButton>
                </MUI.Stack>
            </MUI.Grid>
        </MUI.Grid>
    )
};