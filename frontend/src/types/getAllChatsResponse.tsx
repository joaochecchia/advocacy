import { ChatResponse } from "./chatResponse";

export interface GetAllChatsResponse {
  message: string;
  body: ChatResponse[];
}
