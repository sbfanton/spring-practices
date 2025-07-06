import { getLoadingSetter } from './loadingBridge';

export const fetchWithLoader = async (input, init = {}) => {
  const setLoading = getLoadingSetter();
  try {
    setLoading(true);
    const response = await fetch(input, init);
    return response;
  } catch (error) {
    throw error;
  } finally {
    setTimeout(
        () => setLoading(false),
        1000
    );
  }
};
