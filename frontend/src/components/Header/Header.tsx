import * as MUI from '@mui/material'

export default () => {
    return (
        <MUI.Grid container spacing={2} justifyContent="center" className="bg-black p-1">
            <MUI.Grid xs={10} item={true} >
                <MUI.Stack  spacing={{ xs: 1, sm: 1 }} direction="row" useFlexGap flexWrap="wrap">
                    <MUI.Button variant="contained" href="/">Home</MUI.Button>
                    <MUI.Button variant="contained" href="/login">Login</MUI.Button>
                    <MUI.Button variant="contained" href="/register">Register</MUI.Button>
                </MUI.Stack>
            </MUI.Grid>
        </MUI.Grid>
    )
};