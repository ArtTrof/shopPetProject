import axios from "axios";
import * as React from "react";

function App() {

  const [firstName, setFirstName] = React.useState("");
  const [lastName, setLastName] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");


  const onRegisterSubmit = async (e: React.FormEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const response = await axios.post("http://localhost:9090/signup", {
      email,
      password,
      firstName,
      lastName
    });
    console.log(response);

  }

  return (
    <>
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
    </>
  );
}

export default App;