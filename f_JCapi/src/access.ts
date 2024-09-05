/**
 * @see https://umijs.org/docs/max/access#access
 * */
export default function access(initialState: InitialState | undefined) {
  const { loginUser } = initialState ?? {};
  return {
    canUser: loginUser,
    canAdmin: loginUser && loginUser?.userRole === 'admin',
    // canAdmin: loginUser && loginUser?.userRole === 'user',
  };
}
