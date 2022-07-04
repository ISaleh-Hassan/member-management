import { createGlobalState } from "react-hooks-global-state";
//specify all global states within createGlobalState method
export const{setGlobalState, useGlobalState} = createGlobalState({
isAuthenticated:false
});
