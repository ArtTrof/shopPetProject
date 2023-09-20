import axios from 'axios';
import { API_URL } from '../constants';

export const customerService = {
  signup: async (email: string, password: string, firstName: string, lastName: string, phone?: string) => {
    return await axios.post(`${API_URL}/signup`, {
      email,
      password,
      firstName,
      lastName,
      phone,
    });
  },
  login: async (email: string, password: string) => {
    return await axios.post(`${API_URL}/login`, {
      email,
      password,
    });
  },
};
