import { Moment } from 'moment';

export interface IArticle {
  id?: number;
  newsContent?: string;
  dateDetected?: string;
  emailAddress?: string;
  isDeleted?: boolean;
  labelIdId?: number;
}

export const defaultValue: Readonly<IArticle> = {
  isDeleted: false,
};
