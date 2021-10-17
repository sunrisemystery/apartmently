import {Injectable} from '@angular/core';
// import * as List from 'src/assets/static/bad-words.json'
import * as List from 'src/assets/static/tmp-bad.json';

@Injectable({
  providedIn: 'root'
})

export class BadWordService {

  public myList = [];
  public placeholder = '*';
  // public regex = /[^a-zA-Z0-9|\$|\@]|\^/g;
  public replaceRegex = /\w/g;

  constructor() {
    this.myList = Array.prototype.concat.apply(List);
    this.myList = this.myList[0].default
  }


  public check(checkWord: string): boolean {

    return (
      this.myList.filter(word => {

        const exp = new RegExp(`\\b.{0,2}${word.slice(0, word.length - 1).replace(/(\W)/g, '\\$1')}.{0,4}\\b`
          , 'gi');

        return exp.test(checkWord);
      }).length > 0 || false
    );
  }

  private replace(word: string): string {
    return word.replace(this.replaceRegex, this.placeholder);
  }

  public clean(cleanWord: string): string {
    cleanWord = cleanWord.replace("1", "i");
    cleanWord = cleanWord.replace("!", "i");
    cleanWord = cleanWord.replace("3", "e");
    cleanWord = cleanWord.replace("4", "a");
    cleanWord = cleanWord.replace("@", "a");
    cleanWord = cleanWord.replace("0", "o");
    cleanWord = cleanWord.replace("9", "g");
    cleanWord = cleanWord.replace("v", "u");
    cleanWord = cleanWord.replace("00", "u");
    cleanWord = cleanWord.replace("oo", "u");
    return cleanWord
      .split(/\b/)
      .map(word => {
        return this.check(word) ? this.replace(word) : word;
      })
      .join('');
  }

}

