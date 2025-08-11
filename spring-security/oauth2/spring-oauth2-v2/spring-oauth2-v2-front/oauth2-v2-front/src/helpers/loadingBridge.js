let externalSetLoading = () => {};

export const setLoadingSetter = (fn) => {
  externalSetLoading = fn;
};

export const getLoadingSetter = () => externalSetLoading;
