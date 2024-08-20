import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";

export const logIn = async (phone, password) => {
  const response = await httpClient.post(API.LOGIN, {
    phone: phone,
    password: password,
  });

  setToken(response.data?.data);

  return response;
};

export const logOut = () => {
  removeToken();
};

export const isAuthenticated = () => {
  return getToken();
};
