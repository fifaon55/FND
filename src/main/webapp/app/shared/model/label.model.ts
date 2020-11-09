import { IArticle } from 'app/shared/model/article.model';
import { LabelType } from 'app/shared/model/enumerations/label-type.model';

export interface ILabel {
  id?: number;
  label?: LabelType;
  ids?: IArticle[];
}

export const defaultValue: Readonly<ILabel> = {};
