import messagesKo from '@/messages/messages_ko.properties'
import messagesEn from '@/messages/messages_en.properties'

export const loadMessages = () => {
  const userLanguage = getUserLanguage()

  if (userLanguage === 'ko') {
    return messagesKo
  } else if (userLanguage === 'en') {
    return messagesEn
  }
  return messagesEn
}

export const getUserLanguage = () => {
  return 'en'
}
