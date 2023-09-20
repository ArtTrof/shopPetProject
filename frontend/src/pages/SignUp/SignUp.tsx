import * as React from "react";

import { Button, Grid, TextField, Typography} from '@mui/material';

import { customerService } from "@/services";

export default () => {
    const [firstName, setFirstName] = React.useState("");
    const [lastName, setLastName] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [rePassword, setRePassword] = React.useState("");
    const [isPasswordSimilar, setIsPasswordSimilar] = React.useState(true)
    const [phone, setPhone] = React.useState("");
    const [reqRes, setReqRes] = React.useState("");

    const checkPassword = () => {
        if(password === rePassword) {
        setIsPasswordSimilar(true);
        } else {
        setIsPasswordSimilar(false)
        }
    };

    const onRegisterSubmit = async (e: React.FormEvent<HTMLButtonElement>) => {
        e.preventDefault();
        customerService.signup(email, password, firstName, lastName, phone)
        .then(() => setReqRes("Successfully registered!"))
        .catch(() => setReqRes("Failed to register!"));
    }

    return (
    <div className="w-1/2 mx-auto my-0 p-11 bg-[#E6EAFF] my-32">
        <Typography variant="h4" className="text-center !text-2xl !font-bold !mb-6">Registration</Typography>
        <Typography variant="h6" className="text-center !font-bold !mb-2 text-rose-500">{reqRes}</Typography>
        <Grid container spacing={4}>
            <Grid item xs={6} className="w-11/12">
                <TextField className="!m-4"
                label="First Name" variant="outlined" value={firstName} onInput={(e: React.FormEvent<HTMLInputElement>) => {setFirstName((e.target as HTMLInputElement).value)}}
                color="secondary"/>
                <TextField className="!m-4"
                label="Last Name" variant="outlined" value={lastName} onInput={(e: React.FormEvent<HTMLInputElement>) => {setLastName((e.target as HTMLInputElement).value)}}
                color="secondary"/>
                <TextField className="!m-4"
                label="Email" variant="outlined" value={email} onInput={(e: React.FormEvent<HTMLInputElement>) => {setEmail((e.target as HTMLInputElement).value)}}
                color="secondary"/>
            </Grid>
            <Grid item xs={6} className="w-11/12">
                <TextField className="!m-4"
                label="Password" variant="outlined" type="password" value={password} onInput={(e: React.FormEvent<HTMLInputElement>) => {setPassword((e.target as HTMLInputElement).value)}}
                color="secondary"/>
                {isPasswordSimilar
                ?
                <TextField className="!m-4"
                onBlur={() => {checkPassword()}}
                label="Confirm Password" variant="outlined" type="password" value={rePassword} onInput={(e: React.FormEvent<HTMLInputElement>) => {setRePassword((e.target as HTMLInputElement).value)}}
                color="secondary"/>
                :
                <TextField className="!m-4"
                onBlur={() => {checkPassword()}}
                error
                label="Incorrect Password" id="outlined-error" type="password" value={rePassword} onInput={(e: React.FormEvent<HTMLInputElement>) => {setRePassword((e.target as HTMLInputElement).value)}}
                color="secondary"/>
                }
                <TextField className="!m-4"
                label="Phone Number" variant="outlined" type="number" value={phone} onInput={(e: React.FormEvent<HTMLInputElement>) => {setPhone((e.target as HTMLInputElement).value)}}
                color="secondary"/>
            </Grid>
            <Grid item xs={6} display="flex" alignItems="center" justifyContent="center">
                <Button onClick={onRegisterSubmit} type="submit"
                className="!py-4 !bg-sky-500 !w-40 !rounded-3xl mx-auto my-0">
                Sign Up
                </Button>
            </Grid>
            <Grid item xs={6}  display="flex" alignItems="center" justifyContent="center">
                <a className="hover:text-sky-500" href="">Forgot Your Password?</a>
            </Grid>
        </Grid>
    </div>
    )
};