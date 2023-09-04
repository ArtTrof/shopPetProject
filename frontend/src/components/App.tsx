import * as React from "react";
import { customerService } from "../services/customer";

import * as MUI from '@mui/material'

import Header from "./Header";

export default () =>  {

  const [firstName, setFirstName] = React.useState("");
  const [lastName, setLastName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [reqRes, setReqRes] = React.useState("");

  const onRegisterSubmit = async (e: React.FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    customerService.signup(email, password, firstName, lastName)
      .then(() => setReqRes("Successfully registered!"))
      .catch(() => setReqRes("Failed to register!"));
  }

  return (
    <>
    <Header />
    <MUI.Grid container spacing={2}>
      <MUI.Grid xs={10} item={true}>
        <h1>ReGistRRRRRati0n</h1>
        <div>
          <p>EMAIL</p>
          <input type="email" value={email} onInput={(e: React.FormEvent<HTMLInputElement>) => {setEmail((e.target as HTMLInputElement).value)}} />
        </div>
        <div>
          <p>First Name</p>
          <input type="text" value={firstName} onInput={(e: React.FormEvent<HTMLInputElement>) => {setFirstName((e.target as HTMLInputElement).value)}} />
        </div>
        <div>
          <p>Last Name</p>
          <input type="text" value={lastName} onInput={(e: React.FormEvent<HTMLInputElement>) => {setLastName((e.target as HTMLInputElement).value)}} />
        </div>
        <div>
          <p>Password</p>
          <input type="password" value={password} onInput={(e: React.FormEvent<HTMLInputElement>) => {setPassword((e.target as HTMLInputElement).value)}} />
        </div>
        <button onClick={onRegisterSubmit}>Register</button>
        <p>{reqRes}</p>
      </MUI.Grid>
    </MUI.Grid>
    </>
  );
};